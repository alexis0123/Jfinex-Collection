package com.jfinex.collection.data.local.converters

import androidx.room.TypeConverter
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class StudentMapConverter {

    private val mapSerializer = MapSerializer(String.Companion.serializer(), Int.serializer())

    @TypeConverter
    fun fromMap(map: Map<String, Int>): String {
        return Json.Default.encodeToString(mapSerializer, map)
    }

    @TypeConverter
    fun toMap(data: String): Map<String, Int> {
        return Json.Default.decodeFromString(mapSerializer, data)
    }
}