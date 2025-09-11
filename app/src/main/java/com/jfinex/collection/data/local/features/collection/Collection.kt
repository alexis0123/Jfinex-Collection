package com.jfinex.collection.data.local.features.collection

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
@Entity(tableName = "collections")
data class Collection(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    @Contextual val date: LocalDate,
    val name: String,
    val block: String,
    val officerName: String,
    val item: String? = null,
    val category: String? = null,
    val receiptNumber: Int? = null,
    val new: Boolean = true,
    val comment: String = ""
)