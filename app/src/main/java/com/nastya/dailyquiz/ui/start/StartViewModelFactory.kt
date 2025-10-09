package com.nastya.dailyquiz.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastya.dailyquiz.data.local.dao.HistoryDao

class StartViewModelFactory(private val dao: HistoryDao):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}