package com.nastya.dailyquiz.domain.model

data class QuizQuestion (
    val question: String,
    val correctAnswer: String,
    val allAnswers: List<String>,
    var chooseAnswer: String = "",
    val category: String,
    val difficulty: String,
)