package com.jfinex.collection.data.local.database

import android.app.Application
import androidx.room.Room
import com.jfinex.collection.data.local.features.collection.CollectionDao
import com.jfinex.collection.data.local.features.fields.FieldDao
import com.jfinex.collection.data.local.features.receipt.ReceiptDao
import com.jfinex.collection.data.local.features.students.StudentDao
import com.jfinex.collection.data.local.features.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "jfinex_admin_db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun providesCollectionDao(db: AppDatabase): CollectionDao = db.collectionDao()

    @Provides
    fun providesFieldDao(db: AppDatabase): FieldDao = db.fieldDao()

    @Provides
    fun providesStudentDao(db: AppDatabase): StudentDao = db.studentDao()

    @Provides
    fun providesUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun providesReceiptDao(db: AppDatabase): ReceiptDao = db.receiptDao()

}