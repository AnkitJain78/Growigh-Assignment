package com.example.assignment.data.remote.dto

data class FeedResponse(
    val status: String = "",
    val totalResults: Int = 0,
    val articles: List<FeedDto>
)
