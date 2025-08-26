package com.nastya.dailyquiz

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val response_code: Int,
    val results: List<Question>
)

@Serializable
data class Question (
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)

data class QuestionParse (
    val question: String,
    val correctAnswer: String,
    val allAnswers: List<String>,
    var chooseAnswer: String = "",
)