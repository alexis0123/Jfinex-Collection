package com.jfinex.collection.ui.pager.page.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.collection.data.local.features.students.Student
import com.jfinex.collection.data.local.features.students.StudentRepository
import com.jfinex.collection.ui.pager.page.collection.components.similarity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class StudentSearchViewModel @Inject constructor(
    private val studentRepo: StudentRepository
) : ViewModel() {

    private val allStudents = studentRepo.getAll()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _blockFilter = MutableStateFlow<String?>(null)
    val blockFilter: StateFlow<String?> = _blockFilter

    private val _studentIsSelected = MutableStateFlow(false)
    val studentIsSelected: StateFlow<Boolean> = _studentIsSelected

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _selectedStudent = MutableStateFlow<Student?>(null)
    val selectedStudent: StateFlow<Student?> = _selectedStudent

    val results: StateFlow<List<Student>> =
        combine(_query, _blockFilter, allStudents) { query, block, students ->
            Triple(query.lowercase(), block, students)
        }
            .onEach { _isLoading.value = true }
            .debounce(100)
            .map { (query, block, students) ->
                if (query.isBlank()) emptyList()
                else {
                    students.asSequence()
                        .filter { student ->
                            student.name.contains(query, ignoreCase = true) &&
                                    (block.isNullOrBlank() || student.block.equals(block, ignoreCase = true))
                        }
                        .sortedWith(
                            compareByDescending<Student> { it.name.startsWith(query, ignoreCase = true) }
                                .thenBy { similarity(query, it.name.lowercase()) }
                        )
                        .take(50)
                        .toList()
                }
            }
            .onEach { _isLoading.value = false }
            .flowOn(Dispatchers.Default)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun updateBlock(block: String?) {
        _blockFilter.value = block
    }

    fun selectStudent() {
        _studentIsSelected.value = true
    }

    fun getStudent(student: Student) {
        _selectedStudent.value = student
    }

    fun clear() {
        updateQuery("")
        updateBlock("")
        _selectedStudent.value = null
        if (_studentIsSelected.value) _studentIsSelected.value = false
    }

}