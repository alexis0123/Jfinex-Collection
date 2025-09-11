package com.jfinex.collection.data.local.features.receipt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReceiptDao {

    @Insert
    suspend fun addReceipt(receipt: Receipt)

    @Update
    suspend fun updateReceipt(receipt: Receipt)

    @Delete
    suspend fun removeReceipt(receipt: Receipt)

    @Query("SELECT * FROM receipt")
    suspend fun getAllReceipt(): List<Receipt>

    @Query("DELETE FROM receipt")
    suspend fun clear()

    @Query(
        """
        SELECT * FROM receipt
        WHERE name = :name 
          AND block = :block 
          AND item = :item
        """
    )
    suspend fun getReceipt(
        name: String,
        block: String,
        item: String
    ): Receipt?

}