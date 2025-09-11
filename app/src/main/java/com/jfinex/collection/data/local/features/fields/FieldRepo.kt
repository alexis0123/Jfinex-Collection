package com.jfinex.collection.data.local.features.fields

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FieldRepository @Inject constructor(
    private val dao: FieldDao
) {
    fun getAll(): Flow<List<Field>> = dao.getFieldsFlow()
    suspend fun insert(field: Field) = dao.insert(field)
    suspend fun clear() = dao.clear()
}