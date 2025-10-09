package com.nastya.dailyquiz.ui.result

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.nastya.dailyquiz.data.local.entity.History
import com.nastya.dailyquiz.data.local.dao.HistoryDao
import com.nastya.dailyquiz.domain.model.QuizQuestion
import com.nastya.dailyquiz.R
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ResultViewModel(private val savedStateHandle: SavedStateHandle, val dao: HistoryDao): ViewModel() {
    private val questionsJson = savedStateHandle.get<String>("questions") ?: ""
    val questions = Gson().fromJson(questionsJson, Array<QuizQuestion>::class.java).toList()

    val countQuestions = questions.count()
    val countCorrectQuestions = questions.count{ it.chooseAnswer == it.correctAnswer }

    fun getResHeading(context: Context) : String{
        return when (countCorrectQuestions) {
            4 -> context.getString(R.string.almost_perfect)
            3 -> context.getString(R.string.good)
            2 -> context.getString(R.string.almost_good)
            1 -> context.getString(R.string.bad)
            0 -> context.getString(R.string.worse)
            else -> context.getString(R.string.perfect)
        }
    }

    fun getResSubtitle(context: Context) : String{
        return when (countCorrectQuestions) {
            4 -> context.getString(R.string.four_star )
            3 -> context.getString(R.string.three_star)
            2 -> context.getString(R.string.two_star)
            1 -> context.getString(R.string.one_star)
            0 -> context.getString(R.string.zero_star)
            else -> context.getString(R.string.five_star)
        }
    }

    fun addHistory() {
        viewModelScope.launch {
            val quiz = History(
                countCorrectQuestions = countCorrectQuestions,
                quizDateTime = LocalDateTime.now(),
                quizCategory = questions[0].category,
                quizDifficulty = questions[0].difficulty
            )
            dao.insert(quiz)
        }
    }
}