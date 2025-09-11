package com.jfinex.collection.ui.csv

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CsvViewModel @Inject constructor(private val repo: CsvRepository) : ViewModel() {

    private val _validRows = MutableStateFlow<List<List<String>>>(emptyList())
    val validRows = _validRows.asStateFlow()

    private val _ignoredRows = MutableStateFlow<List<List<String>>>(emptyList())
    val ignoredRows = _ignoredRows.asStateFlow()

    private val _fileName = MutableStateFlow<String?>(null)
    val fileName = _fileName.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _hasFile = MutableStateFlow(false)
    val hasFile = _hasFile.asStateFlow()

    fun loadCsv(contentResolver: ContentResolver, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _error.value = null
            try {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (ignored: Exception) { /* ignore */ }

            try {
                val parsed = repo.readCsvFromUri(contentResolver, uri)
                _validRows.value = parsed.valid
                _ignoredRows.value = parsed.ignored
                _fileName.value = repo.getFileName(contentResolver, uri)
                _hasFile.value = true
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to read CSV"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clear() {
        _validRows.value = emptyList()
        _ignoredRows.value = emptyList()
        _fileName.value = null
        _error.value = null
        _hasFile.value = false
    }
}