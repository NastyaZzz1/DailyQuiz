package com.nastya.dailyquiz

import androidx.lifecycle.ViewModel

class StartViewModel(dao: HistoryDao): ViewModel() {
    val countQuestions = 5
    val quizzes = dao.getAll()
}