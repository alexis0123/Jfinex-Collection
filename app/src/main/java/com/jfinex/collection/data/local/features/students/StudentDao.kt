package com.jfinex.collection.data.local.features.students

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Query("SELECT * FROM students")
    fun getStudents(): Flow<List<Student>>

    @Insert
    suspend fun insert(student: Student)

    @Query("DELETE FROM students")
    suspend fun clear()

    @Query("SELECT * FROM students WHERE name = :name AND block = :block")
    suspend fun getStudent(name: String, block: String): Student?

}