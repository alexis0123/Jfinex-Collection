package com.jfinex.collection.data.local.converters

import androidx.room.TypeConverter

const val separator = " <<?/* SEPARATOR ?/*>> "

class ListConverter {

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(separator)
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        return if (string.isEmpty()) emptyList() else string.split(separator)
    }

}