package com.jfinex.collection.ui.pager.page.collection.dialog.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.collection.data.local.components.dateToday
import com.jfinex.collection.data.local.features.collection.Collection
import com.jfinex.collection.data.local.features.collection.CollectionRepo
import com.jfinex.collection.data.local.features.receipt.ReceiptRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptGeneratorViewModel @Inject constructor(
    private val receiptRepo: ReceiptRepo,
    private val collectionRepo: CollectionRepo
): ViewModel() {
    private val _newCollections = MutableStateFlow<List<Collection>>(emptyList())
    val newCollections: StateFlow<List<Collection>> = _newCollections

    private val _alreadyExists = MutableStateFlow<List<Collection>>(emptyList())
    val alreadyExists: StateFlow<List<Collection>> = _alreadyExists

    fun generateReceipt(
        name: String,
        block: String,
        officerName: String,
        item: String,
        category: String,
        receiptNumber: Int,
        comment: String
    ) {
        viewModelScope.launch {
            val existing = receiptRepo.getReceipt(
                name = name,
                block = block,
                item = item
            )
            val collection = Collection(
                type = "Receipt",
                date = dateToday(),
                name = name,
                block = block,
                officerName = officerName,
                item = item,
                category = category,
                receiptNumber = receiptNumber,
                comment = comment
            )
            if (existing == null) {
                receiptRepo.addReceipt(
                    date = dateToday(),
                    name = name,
                    block = block,
                    officerName = officerName,
                    item = item,
                    category = category,
                    receiptNumber = receiptNumber,
                    comment = comment
                )
                collectionRepo.addCollection(
                    type = "Receipt",
                    date = dateToday(),
                    name = name,
                    block = block,
                    officerName = officerName,
                    item = item,
                    category = category,
                    receiptNumber = receiptNumber,
                    comment = comment
                )
                _newCollections.value = _newCollections.value + collection
            } else {
                _alreadyExists.value = _alreadyExists.value + collection
            }
        }
    }

    fun clear() {
        _newCollections.value = emptyList()
        _alreadyExists.value = emptyList()
    }

}