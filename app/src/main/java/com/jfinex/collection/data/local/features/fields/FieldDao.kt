package com.jfinex.collection.data.local.features.fields

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldDao {

    @Query("SELECT * FROM fields")
    fun getFieldsFlow(): Flow<List<Field>>

    @Insert
    suspend fun insert(field: Field)

    @Query("DELETE FROM fields")
    suspend fun clear()

}