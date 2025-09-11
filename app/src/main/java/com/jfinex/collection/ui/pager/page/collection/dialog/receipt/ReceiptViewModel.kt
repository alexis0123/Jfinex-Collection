package com.jfinex.collection.ui.pager.page.collection.dialog.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfinex.collection.data.local.features.receipt.Receipt
import com.jfinex.collection.data.local.features.receipt.ReceiptRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val repo: ReceiptRepo
): ViewModel() {

    private var _receipt = MutableStateFlow<Receipt?>(null)
    val receipt: StateFlow<Receipt?> = _receipt

    private val _receipts = MutableStateFlow<List<Receipt>>(emptyList())
    val receipts: StateFlow<List<Receipt>> = _receipts

    fun clear() {
        _receipt.value = null
    }

    fun getAll() {
        viewModelScope.launch {
            _receipts.value = repo.getAll()
        }
    }

    fun removeByNameBlockItem(name: String, block: String, item: String) {
        viewModelScope.launch {
            val receipt = repo.getReceipt(name, block, item)
            if (receipt != null) {
                repo.removeReceipt(receipt)
            }
        }
    }


}