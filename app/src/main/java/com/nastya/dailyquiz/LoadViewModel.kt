package com.nastya.dailyquiz

import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.collections.shuffled

class LoadViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private var responseQuestion: List<Question> = emptyList()
    private val _questions = MutableLiveData<List<QuestionParse>>()
    val questions: LiveData<List<QuestionParse>> = _questions
    val countQuestions = savedStateHandle.get<Int>("count_questions") ?: 0

    companion object {
        const val BASE_URL = "https://opentdb.com/"
    }

    fun getQuestions() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)

        val retrofit = Retrofit
            .Builder()
            .client(client)
            .addConverterFactory(converterFactory)
            .baseUrl(BASE_URL)
            .build()

        val service = retrofit.create(QuestionsService::class.java)

        service.getQuestions(countQuestions, "multiple").enqueue (
            object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) { val apiResponse = response.body()
                        responseQuestion = apiResponse?.results ?: emptyList()
                        _questions.postValue(setQuestionParse())
                    } else {
                        Log.e("Retrofit", "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                    Log.e("Retrofit", "Failure: ${t.message}")
                }
            }
        )
    }

    fun setQuestionParse(): List<QuestionParse> {
        return responseQuestion.map { question ->
            QuestionParse(
                question = HtmlCompat.fromHtml(question.question, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                correctAnswer = HtmlCompat.fromHtml(question.correct_answer, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                allAnswers = (question.incorrect_answers + question.correct_answer).shuffled(),
                chooseAnswer = ""
            )
        }
    }
}