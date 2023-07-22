package com.example.assignment.data.remote

import com.example.assignment.data.remote.dto.FeedDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi {
    companion object {
        const val BASE_URL = "https://api.unsplash.com"
    }

    @GET("/photos")
    suspend fun getFeeds(@Query("page") page: Int): Response<List<FeedDto>>
}
