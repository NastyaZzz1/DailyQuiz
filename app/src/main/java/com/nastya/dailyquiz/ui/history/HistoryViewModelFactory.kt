package com.nastya.dailyquiz.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastya.dailyquiz.data.local.dao.HistoryDao

class HistoryViewModelFactory(
    private val dao: HistoryDao,
    val context: Context
):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(dao, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}