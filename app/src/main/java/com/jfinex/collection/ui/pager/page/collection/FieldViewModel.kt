package com.jfinex.collection.ui.pager.page.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.collection.data.local.features.fields.Field
import com.jfinex.collection.data.local.features.fields.FieldRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FieldViewModel @Inject constructor(
    repo: FieldRepository): ViewModel() {

    val fields: StateFlow<List<Field>> = repo.getAll()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _selectedFields = MutableStateFlow<Map<String, String>>(emptyMap())
    val selectedFields: StateFlow<Map<String, String>> = _selectedFields

    private val _commentPerField = MutableStateFlow<Map<String, String>>(emptyMap())
    val commentPerField: StateFlow<Map<String, String>> = _commentPerField

    fun addToSelectedFields(fieldName: String, category: String) {
        _selectedFields.value += mapOf(fieldName to category)
    }

    fun removeToSelectedFields(fieldName: String) {
        _selectedFields.value -= fieldName
        _commentPerField.value -= fieldName
    }

    fun clear() {
        _selectedFields.value = mapOf()
        _commentPerField.value = mapOf()
    }

    fun addComment(fieldName: String, comment: String) {
        if (comment.isNotBlank()) { _commentPerField.value += mapOf(fieldName to comment) }
    }

    fun removeComment(fieldName: String) {
        _commentPerField.value -= fieldName
    }

}