package com.jfinex.collection.ui.field

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FieldViewModelUi @Inject constructor(private val repo: FieldsRepository): ViewModel() {
    private val _fields = MutableStateFlow<List<Field>>(emptyList())
    val fields = _fields.asStateFlow()

    fun addField(field: Field) {
        repo.addField(field)
        _fields.value += field
    }

    fun removeField(field: Field) {
        repo.removeField(field)
        _fields.value -= field
    }

    fun reset() {
        repo.reset()
        _fields.value = emptyList()
    }

}