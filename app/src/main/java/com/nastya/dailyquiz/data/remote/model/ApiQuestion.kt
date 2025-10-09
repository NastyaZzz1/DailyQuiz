package com.nastya.dailyquiz.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiQuestion (
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)