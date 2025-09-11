package com.jfinex.collection.ui.dialog.tally

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDate

class Converter : TypeAdapter<LocalDate>() {
    private val converter = com.jfinex.collection.data.local.converters.LocalDateConverter()

    override fun write(out: JsonWriter, value: LocalDate?) {
        out.value(converter.fromLocalDate(value))
    }

    override fun read(reader: JsonReader): LocalDate? {
        return converter.toLocalDate(reader.nextString())
    }
}