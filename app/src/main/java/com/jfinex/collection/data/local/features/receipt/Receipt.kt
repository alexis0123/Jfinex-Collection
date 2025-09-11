package com.jfinex.collection.data.local.features.receipt

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
@Entity(tableName = "receipt")
data class Receipt(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val block: String,
    @Contextual val date: LocalDate,
    val item: String,
    val category: String,
    val receiptNumber: Int,
    val officerName: String,
    val comment: String = "",
    val new: Boolean = true
)
