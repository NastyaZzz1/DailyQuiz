package com.nastya.dailyquiz.data.remote.api

import com.nastya.dailyquiz.data.remote.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("type") type: String? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("category") category: Int? = null,
    ): Response<ApiResponse>
}