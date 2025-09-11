package com.jfinex.collection.data.local.features.receipt

import java.time.LocalDate
import javax.inject.Inject

class ReceiptRepo @Inject constructor(
    private val dao: ReceiptDao
) {
    suspend fun addReceipt(
        date: LocalDate,
        name: String,
        block: String,
        officerName: String,
        item: String,
        category: String,
        receiptNumber: Int,
        comment: String
    ) {
        dao.addReceipt(
            Receipt(
                date = date,
                name = name,
                block = block,
                officerName = officerName,
                item = item,
                category = category,
                receiptNumber = receiptNumber,
                comment = comment
            )
        )
    }

    suspend fun removeReceipt(receipt: Receipt) {
        dao.removeReceipt(receipt = receipt)
    }

    suspend fun getReceipt(name: String, block: String, item: String): Receipt? {
        return dao.getReceipt(name = name, block = block, item = item)
    }

    suspend fun clear() = dao.clear()

    suspend fun getAll(): List<Receipt> = dao.getAllReceipt()

    suspend fun update(receipt: Receipt) {
        dao.updateReceipt(receipt = receipt)
    }

}