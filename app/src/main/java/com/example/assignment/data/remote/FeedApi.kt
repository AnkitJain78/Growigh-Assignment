package com.example.assignment.data.remote

import com.example.assignment.data.remote.dto.FeedResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedApi {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "8bbf7303f81a4baca01602930d55bd74"
    }

    @GET("top-headlines?language=en&apikey=$API_KEY")
    suspend fun getFeeds(@Query("page") page: Int): Response<FeedResponse>
}
