package com.jfinex.collection.data.local.features.collection

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Insert
    suspend fun addCollection(collection: Collection)

    @Delete
    suspend fun deleteCollection(collection: Collection)

    @Update
    suspend fun updateCollection(collection: Collection)

    @Query("DELETE FROM collections")
    suspend fun clear()

    @Query("SELECT * FROM collections")
    fun getAllCollections(): Flow<List<Collection>>

    @Query("SELECT DISTINCT item FROM collections WHERE item IS NOT NULL")
    fun getAllItems(): Flow<List<String>>

}