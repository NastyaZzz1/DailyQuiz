package com.nastya.dailyquiz

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quiz_id")
    var quizId: Long = 0L,

    @ColumnInfo(name = "count_correct_questions")
    var countCorrectQuestions: Int = 0,

    @ColumnInfo(name = "quiz_date")
    var quizDateTime: LocalDateTime
)