package com.jfinex.collection.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jfinex.collection.data.local.converters.ListConverter
import com.jfinex.collection.data.local.converters.LocalDateConverter
import com.jfinex.collection.data.local.converters.StudentMapConverter
import com.jfinex.collection.data.local.features.collection.Collection
import com.jfinex.collection.data.local.features.collection.CollectionDao
import com.jfinex.collection.data.local.features.fields.Field
import com.jfinex.collection.data.local.features.fields.FieldDao
import com.jfinex.collection.data.local.features.receipt.Receipt
import com.jfinex.collection.data.local.features.receipt.ReceiptDao
import com.jfinex.collection.data.local.features.students.Student
import com.jfinex.collection.data.local.features.students.StudentDao
import com.jfinex.collection.data.local.features.user.User
import com.jfinex.collection.data.local.features.user.UserDao

@Database(
    entities = [
        Collection::class,
        Student::class,
        Field::class,
        User::class,
        Receipt::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ListConverter::class,
    StudentMapConverter::class,
    LocalDateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
    abstract fun fieldDao(): FieldDao
    abstract fun studentDao(): StudentDao
    abstract fun userDao(): UserDao
    abstract fun receiptDao(): ReceiptDao
}