package com.jfinex.collection.ui.dialog.tally

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.google.gson.GsonBuilder
import com.jfinex.collection.data.local.features.collection.CollectionDao
import com.jfinex.collection.data.local.features.fields.FieldDao
import com.jfinex.collection.data.local.features.receipt.ReceiptDao
import com.jfinex.collection.data.local.features.receipt.json
import com.jfinex.collection.data.local.features.user.UserDao
import com.jfinex.collection.ui.config.components.xor.xorDe
import com.jfinex.collection.ui.config.components.xor.xorEn
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import java.time.LocalDate
import javax.inject.Inject

class TallyRepo @Inject constructor(
    private val userDao: UserDao,
    private val fieldDao: FieldDao,
    private val collectionDao: CollectionDao,
    private val receiptDao: ReceiptDao
) {

    suspend fun exportTally(uri: Uri, contentResolver: ContentResolver) : Uri {
        val user = userDao.getUser().first() ?: throw IllegalStateException("No user found")

        val fields = fieldDao.getFieldsFlow().first()
        val collections = collectionDao.getAllCollections().first()
        val receipts = receiptDao.getAllReceipt()

        val tallyData = TallyData(
            officerName = user.name,
            fields = fields,
            collections = collections,
            receipts = receipts
        )

        val json = json.encodeToString(TallyData.serializer(), tallyData)
        val encrypted = xorEn(json)

        Log.d("Decrypted Data", xorDe(encrypted))

        contentResolver.openOutputStream(uri)?.use { out ->
            out.writer(Charsets.UTF_8).use { writer ->
                writer.write(encrypted)
            }
        }

        receipts.filter { it.new }.forEach { receipt ->
            receiptDao.updateReceipt(receipt.copy(new = false))
        }
        collections.filter { it.new }.forEach { collection ->
            collectionDao.updateCollection(collection.copy(new = false))
        }

        return uri
    }
}
