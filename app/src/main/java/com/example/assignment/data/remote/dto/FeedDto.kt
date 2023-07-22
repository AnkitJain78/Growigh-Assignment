package com.example.assignment.data.remote.dto

data class FeedDto(
    val alt_description: String,
    val description: String,
    val id: String,
    val isLiked: Boolean = false,
    val likes: Int,
    val urls: Urls,
    val user: User,
    val comments: Int
)

