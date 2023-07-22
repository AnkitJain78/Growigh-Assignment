package com.example.assignment.domain.model

data class Video(
    val title: String,
    val source: String,
    val link: String,
    var isLiked: Boolean = false
)