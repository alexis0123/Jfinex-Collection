package com.jfinex.collection.data.local.features.students

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val block: String,
    val name: String,
    val receiptNumber: Map<String, Int>
)