package com.nastya.dailyquiz.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nastya.dailyquiz.data.local.entity.History
import com.nastya.dailyquiz.data.local.dao.HistoryDao
import com.nastya.dailyquiz.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(private val dao: HistoryDao, val context: Context): ViewModel() {
    data class HistoryModel (
        val history: History,
        var state: HistoryState = HistoryState.INITIAL
    )

    enum class HistoryState {
        INITIAL,
        SELECTED,
        NOT_SELECTED
    }

    private val _quizzes = MutableLiveData<List<HistoryModel>>()
    val quizzes: LiveData<List<HistoryModel>> = _quizzes

    init {
        loadQuizzes()
    }

    private fun loadQuizzes() {
        viewModelScope.launch(Dispatchers.IO) {
            val historyList = dao.getAllNotLive()
            val models = historyList.map { quiz ->
                HistoryModel(history = quiz, state = HistoryState.INITIAL)
            }
            _quizzes.postValue(models)
        }
    }

    fun onQuizLongClicked(quizId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = _quizzes.value ?: return@launch
            val isInitial = currentList.any { quiz ->
                quiz.state == HistoryState.SELECTED && quiz.history.quizId == quizId
            }
            val updatedList = currentList.map { model ->
                if (isInitial) {
                    model.copy(state = HistoryState.INITIAL)
                } else if (model.history.quizId == quizId) {
                    model.copy(state = HistoryState.SELECTED)
                } else {
                    model.copy(state = HistoryState.NOT_SELECTED)
                }
            }
            _quizzes.postValue(updatedList)
        }
    }

    fun onDeleteClicked(quizId: Long, context: Context) {
        viewModelScope.launch {
            val currentList = _quizzes.value ?: return@launch
            val updatedList = currentList.filter { it.history.quizId != quizId }
                .map { it.copy(state = HistoryState.INITIAL) }
            _quizzes.postValue(updatedList)
            val quiz = dao.getNotLive(quizId)
            dao.delete(quiz!!)
            showInfDialog(context)
        }
    }

    fun showInfDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)

        val alertDialog = MaterialAlertDialogBuilder(context)
            .setView(dialogView)
            .create()
        alertDialog.show()

        dialogView.findViewById<TextView>(R.id.alertBtn).setOnClickListener {
            alertDialog.dismiss()
        }
    }
}