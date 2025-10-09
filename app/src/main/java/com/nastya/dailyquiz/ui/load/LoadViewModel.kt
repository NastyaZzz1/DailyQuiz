package com.nastya.dailyquiz.ui.load

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastya.dailyquiz.domain.model.QuizQuestion
import com.nastya.dailyquiz.domain.usecase.GetQuestionsUseCase
import kotlinx.coroutines.launch

class LoadViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val _questions = MutableLiveData<List<QuizQuestion>>()
    val questions: LiveData<List<QuizQuestion>> = _questions
    private val countQuestions = 5
    val selectedCategoryId = savedStateHandle.get<Int>("questions_category_id") ?: 0
    val selectedDifficulty = savedStateHandle.get<String>("questions_difficulty") ?: ""

    private val getQuestionsUseCase = GetQuestionsUseCase()

    fun getQuestions() {
        viewModelScope.launch {
            val result = getQuestionsUseCase(
                count = countQuestions,
                type = "multiple",
                difficulty = selectedDifficulty,
                category = selectedCategoryId
            )

            when {
                result.isSuccess -> {
                    _questions.value = result.getOrNull() ?: emptyList()
                }
                result.isFailure -> {
                    Log.e("LoadViewModel", "Error loading questions", result.exceptionOrNull())
                }
            }
        }
    }
}