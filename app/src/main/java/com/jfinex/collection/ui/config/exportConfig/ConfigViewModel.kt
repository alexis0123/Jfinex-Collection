package com.jfinex.collection.ui.config.exportConfig

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.collection.data.local.features.collection.CollectionRepo
import com.jfinex.collection.data.local.features.fields.Field
import com.jfinex.collection.data.local.features.fields.FieldRepository
import com.jfinex.collection.data.local.features.receipt.ReceiptRepo
import com.jfinex.collection.data.local.features.students.Student
import com.jfinex.collection.data.local.features.students.StudentRepository
import com.jfinex.collection.data.local.features.user.UserRepo
import com.jfinex.collection.ui.config.components.xor.xorDe
import com.jfinex.collection.ui.config.components.xor.xorEn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    val configRepo: ConfigRepository,
    private val fieldRepo: FieldRepository,
    private val studentRepo: StudentRepository,
    private val collectionRepo: CollectionRepo,
    private val receiptRepo: ReceiptRepo,
    private val userRepo: UserRepo
) : ViewModel() {

    private val _exportResult = MutableStateFlow<Result<Unit>?>(null)
    val exportResult: StateFlow<Result<Unit>?> = _exportResult

    private val _importResult = MutableStateFlow<Result<Unit>?>(null)
    val importResult: StateFlow<Result<Unit>?> = _importResult

    private val _importedConfig = MutableStateFlow<Config?>(null)
    val importedConfig: StateFlow<Config?> = _importedConfig

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun exportConfig(
        contentResolver: ContentResolver,
        uri: Uri,
        students: List<List<String>>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!configRepo.hasFields()) throw IllegalStateException("No fields added")
                if (students.isEmpty()) throw IllegalStateException("No students to export")

                val config = configRepo.buildConfig(students)
                val json = xorEn(Json { prettyPrint = false }
                    .encodeToString(Config.serializer(), config))

                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write(json.toByteArray())
                } ?: throw IllegalArgumentException("Unable to open OutputStream for URI: $uri")

                _exportResult.value = Result.success(Unit)
            } catch (e: Exception) {
                _exportResult.value = Result.failure(e)
            }
        }
    }

    fun importConfig(contentResolver: ContentResolver, uri: Uri, outputFile: File) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val encoded = contentResolver.openInputStream(uri)?.use { it.bufferedReader().readText() }
                    ?: throw IllegalArgumentException("Unable to open InputStream for URI: $uri")

                val json = xorDe(encoded)
                val config: Config = Json.decodeFromString(Config.serializer(), json)
                _importResult.value = Result.success(Unit)
                _isLoading.value = false
                fieldRepo.clear()
                studentRepo.clear()
                receiptRepo.clear()
                collectionRepo.clear()
                userRepo.clear()

                config.fields.forEach { (fieldName, categories) ->
                    val newBase = config.newBaseNumbers[fieldName] ?: 0
                    fieldRepo.insert(Field(name = fieldName, categories = categories, newBase = newBase))
                }

                config.studentsWithReceiptNumber.forEach { (key, studentConfig) ->
                    val (nameFromKey, blockFromKey) = parseKey(key)
                    val finalName  = nameFromKey.ifBlank { studentConfig.toString() }
                    val finalBlock = if (blockFromKey.isNotBlank()) blockFromKey else studentConfig.block

                    studentRepo.insert(
                        Student(
                            block = finalBlock,
                            name = finalName,
                            receiptNumber = studentConfig.receipts
                        )
                    )
                }

                _importedConfig.value = config
                outputFile.writeText(json)

            } catch (e: Exception) {
                _importResult.value = Result.failure(e)
            }
        }
    }

    fun reset() {
        _exportResult.value = null
        _importResult.value = null
    }
}