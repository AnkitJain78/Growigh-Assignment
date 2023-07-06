package com.example.assignment.data.remote.dto

data class FeedDto(
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val source: FeedSource
)

