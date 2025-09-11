package com.jfinex.collection.ui.config.exportConfig

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val newBaseNumbers: Map<String, Int>,
    val fields: Map<String, List<String>>,
    val studentsWithReceiptNumber: Map<String, StudentConfig>
)

@Serializable
data class StudentConfig(
    val block: String,
    val receipts: Map<String, Int>
)