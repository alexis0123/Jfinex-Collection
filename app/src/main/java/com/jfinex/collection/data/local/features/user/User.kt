package com.jfinex.collection.data.local.features.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int = 0,
    val name: String
)
