package com.nastya.dailyquiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.gson.Gson

class QuestionViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val _indexQuestions: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val indexQuestions: LiveData<Int> = _indexQuestions

    private val _isAnswerSelected = MutableLiveData(false)
    val isAnswerSelected: LiveData<Boolean> = _isAnswerSelected

    private val questionsJson = savedStateHandle.get<String>("questions") ?: ""
    val questions = Gson().fromJson(questionsJson, Array<QuestionParse>::class.java).toList()

    fun setAnswerSelected(selected: Boolean) {
        _isAnswerSelected.value = selected
    }

    fun increaseIndexQuests() {
        _indexQuestions.value = _indexQuestions.value!! + 1
    }

    fun setChooseAnswer(chooseAnswer: String) {
        questions[indexQuestions.value!!].chooseAnswer = chooseAnswer
    }

    fun getQuestionTitle(): String { return questions[indexQuestions.value!!].question }
    fun getAnswers(): List<String> { return questions[indexQuestions.value!!].allAnswers }
    fun getCorrectAnswer(): String { return questions[indexQuestions.value!!].correctAnswer }
}