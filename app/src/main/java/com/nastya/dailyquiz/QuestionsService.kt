package com.nastya.dailyquiz

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsService {
    @GET("api.php")
    fun getQuestions(
        @Query("amount") amount: Int,
        @Query("type") type: String? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("category") category: Int? = null,
    ): Call<ApiResponse>
}