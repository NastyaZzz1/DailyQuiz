package com.nastya.dailyquiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StartViewModelFactory(private val dao: HistoryDao):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}