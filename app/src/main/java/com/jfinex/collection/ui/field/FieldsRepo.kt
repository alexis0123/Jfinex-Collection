package com.jfinex.collection.ui.field

import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class Field(
    val name: String,
    val category: List<String> = emptyList()
)

@Singleton
class FieldsRepository @Inject constructor() {
    val fields = mutableListOf<Field>()

    fun addField(field: Field) = fields.add(field)
    fun removeField(field: Field) = fields.remove(field)
    fun reset() = fields.clear()

}