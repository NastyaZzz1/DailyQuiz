package com.nastya.dailyquiz

import androidx.lifecycle.ViewModel

class StartViewModel(dao: HistoryDao): ViewModel() {
    val quizzes = dao.getAll()
}