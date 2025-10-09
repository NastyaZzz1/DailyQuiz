package com.nastya.dailyquiz.domain.usecase

import com.nastya.dailyquiz.data.repository.QuestionRepository
import com.nastya.dailyquiz.domain.model.QuizQuestion

class GetQuestionsUseCase {
    private val questionRepository = QuestionRepository()

    suspend operator fun invoke (
        count: Int,
        type: String,
        difficulty: String,
        category: Int
    ): Result<List<QuizQuestion>> {
        return questionRepository.getQuestions(count, type, difficulty, category)
    }
}