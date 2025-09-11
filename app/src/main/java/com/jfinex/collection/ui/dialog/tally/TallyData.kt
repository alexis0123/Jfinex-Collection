package com.jfinex.collection.ui.dialog.tally

import com.jfinex.collection.data.local.features.collection.Collection
import com.jfinex.collection.data.local.features.fields.Field
import com.jfinex.collection.data.local.features.receipt.Receipt
import kotlinx.serialization.Serializable

@Serializable
data class TallyData(
    val officerName: String,
    val fields: List<Field>,
    val collections: List<Collection>,
    val receipts: List<Receipt>
)
