package com.nastya.dailyquiz.ui.start

import androidx.lifecycle.ViewModel
import com.nastya.dailyquiz.data.local.dao.HistoryDao

class StartViewModel(dao: HistoryDao): ViewModel() {
    val quizzes = dao.getAll()
}