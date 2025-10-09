package com.nastya.dailyquiz.data.mapper

import androidx.core.text.HtmlCompat
import com.nastya.dailyquiz.data.remote.model.ApiQuestion
import com.nastya.dailyquiz.domain.model.QuizQuestion

class QuestionMapper {
    fun toDomain(apiQuestion: ApiQuestion): QuizQuestion {
        return QuizQuestion(
            question = HtmlCompat.fromHtml(apiQuestion.question, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString(),
            correctAnswer = HtmlCompat.fromHtml(
                apiQuestion.correct_answer,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString(),
            allAnswers = (apiQuestion.incorrect_answers + apiQuestion.correct_answer).shuffled(),
            chooseAnswer = "",
            category = apiQuestion.category,
            difficulty = apiQuestion.difficulty
        )
    }
}