package com.jfinex.collection.data.local.features.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repo: CollectionRepo
): ViewModel() {

    fun addCollection(
        type: String,
        date: LocalDate,
        name: String,
        block: String,
        officerName: String,
        item: String,
        category: String,
        receiptNumber: Int,
        comment: String
    ) {
        viewModelScope.launch {
            repo.addCollection(
                type = type,
                date = date,
                name = name,
                block = block,
                officerName = officerName,
                item = item,
                category = category,
                receiptNumber = receiptNumber,
                comment = comment
            )
        }
    }

    suspend fun addStudent(
        date: LocalDate,
        name: String,
        block: String,
        officerName: String
    ) {
        repo.addStudent(
            date = date,
            name = name,
            block = block,
            officerName = officerName
        )
    }

    fun delete(collection: Collection) {
        viewModelScope.launch {
            repo.delete(collection = collection)
        }
    }

}