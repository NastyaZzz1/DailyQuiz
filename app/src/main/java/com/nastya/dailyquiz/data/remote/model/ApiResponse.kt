package com.nastya.dailyquiz.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val response_code: Int,
    val results: List<ApiQuestion>
)