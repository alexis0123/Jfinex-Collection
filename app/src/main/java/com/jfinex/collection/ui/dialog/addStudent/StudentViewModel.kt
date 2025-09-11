package com.jfinex.collection.ui.dialog.addStudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.collection.data.local.components.dateToday
import com.jfinex.collection.data.local.features.collection.CollectionRepo
import com.jfinex.collection.data.local.features.fields.Field
import com.jfinex.collection.data.local.features.fields.FieldRepository
import com.jfinex.collection.data.local.features.students.Student
import com.jfinex.collection.data.local.features.students.StudentRepository
import com.jfinex.collection.data.local.features.user.User
import com.jfinex.collection.data.local.features.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val studentRepo: StudentRepository,
    private val fieldRepo: FieldRepository,
    private val collectionRepo: CollectionRepo,
    private val userRepo: UserRepo
): ViewModel() {

    private val user: StateFlow<User?> = userRepo.getUser()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val fields: StateFlow<List<Field>> = fieldRepo.getAll()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addStudent(name: String, block: String) {
        viewModelScope.launch {

            val existingStudent = studentRepo.getStudent(name = name, block = block)

            val blockRegex = Regex("""^[1-4][A-Z]$""")
            if (!blockRegex.matches(block)) return@launch

            if (fields.value.isEmpty()) return@launch

            if (existingStudent != null) return@launch

            val receiptNumbers = fields.value.associate { field ->
                field.name to (field.newBase..field.newBase + 9_999).random()
            }
            studentRepo.insert(
                Student(
                    block = block,
                    name = name,
                    receiptNumber = receiptNumbers
                )
            )
            collectionRepo.addStudent(
                date = dateToday(),
                name = name,
                block = block,
                officerName = user.value!!.name
            )
        }
    }
}