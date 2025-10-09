package com.nastya.dailyquiz.data.repository

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nastya.dailyquiz.data.mapper.QuestionMapper
import com.nastya.dailyquiz.data.remote.api.QuestionsService
import com.nastya.dailyquiz.domain.model.QuizQuestion
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class QuestionRepository {
    private val questionsService: QuestionsService by lazy {
        createQuestionsService()
    }

    companion object {
        const val BASE_URL = "https://opentdb.com/"
    }

    suspend fun getQuestions(
        count: Int,
        type: String,
        difficulty: String,
        category: Int
    ): Result<List<QuizQuestion>> {
        return try {
            val response = questionsService.getQuestions(count, type, difficulty, category)
            if (response.isSuccessful) {
                val apiQuestions = response.body()?.results ?: emptyList()
                val quizQuestions = apiQuestions.map{ QuestionMapper().toDomain(it)}
                Result.success(quizQuestions)
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        }
        catch(e: Exception) {
            Result.failure(e)
        }
    }

    fun createQuestionsService(): QuestionsService {
        val loggingInterceptor = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)

        val retrofit = Retrofit
            .Builder()
            .client(client)
            .addConverterFactory(converterFactory)
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(QuestionsService::class.java)
    }
}