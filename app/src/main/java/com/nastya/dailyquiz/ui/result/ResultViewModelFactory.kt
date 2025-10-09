package com.nastya.dailyquiz.ui.result

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.nastya.dailyquiz.data.local.dao.HistoryDao

class ResultViewModelFactory(
    private val owner: SavedStateRegistryOwner,
    private val dao: HistoryDao,
    private val defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return ResultViewModel(handle, dao) as T
    }
}