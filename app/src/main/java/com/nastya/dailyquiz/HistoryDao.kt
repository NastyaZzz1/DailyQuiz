package com.nastya.dailyquiz

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(quiz: History)

    @Delete
    suspend fun delete(quiz: History)

    @Query("SELECT * FROM history_table WHERE quiz_id = :quizId")
    suspend fun getNotLive(quizId: Long): History?

    @Query("SELECT * FROM history_table ORDER BY quiz_id DESC")
    fun getAll() : LiveData<List<History>>

    @Query("SELECT * FROM history_table ORDER BY quiz_id DESC")
    fun getAllNotLive() : List<History>
}