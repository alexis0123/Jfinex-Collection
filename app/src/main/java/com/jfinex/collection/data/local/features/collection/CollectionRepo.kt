package com.jfinex.collection.data.local.features.collection

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class CollectionRepo @Inject constructor(
    private val dao: CollectionDao
) {

    fun getAll(): Flow<List<Collection>> = dao.getAllCollections()

    fun getByFilter(
        block: String,
        date: List<LocalDate>,
        officerName: List<String>,
        types: List<String>,
        item: List<String>
    ): Flow<List<Collection>> {
        return dao.getAllCollections().map { collections ->
            collections.filter { collection ->
                val blockMatch = block.isBlank() || collection.block == block
                val dateMatch = date.isEmpty() || collection.date in date
                val officerMatch = officerName.isEmpty() || collection.officerName in officerName
                val typeMatch = types.isEmpty() || collection.type in types
                val itemMatch = item.isEmpty() || (collection.item != null && collection.item in item)

                blockMatch && dateMatch && officerMatch && typeMatch && itemMatch
            }
        }
    }


    suspend fun addStudent(
        date: LocalDate,
        name: String,
        block: String,
        officerName: String
    ) {
        dao.addCollection(Collection(
            type = "Added Student",
            date = date,
            name = name,
            block = block,
            officerName = officerName
        ))
    }

    suspend fun addCollection(
        type: String,
        date: LocalDate,
        name: String,
        block: String,
        officerName: String,
        item: String,
        category: String,
        receiptNumber: Int,
        comment: String
    ) {
        dao.addCollection(Collection(
            type = type,
            date = date,
            name = name,
            block = block,
            officerName = officerName,
            item = item,
            category = category,
            receiptNumber = receiptNumber,
            comment = comment
        ))
    }

    suspend fun delete(collection: Collection) {
        dao.deleteCollection(collection)
    }

    suspend fun clear() = dao.clear()

    suspend fun update(collection: Collection) {
        dao.updateCollection(collection = collection)
    }

    fun getAllItems(): Flow<List<String>> = dao.getAllItems()

}