package com.example.assignment.domain.model

import com.example.assignment.data.remote.dto.FeedSource

data class Feed (
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val source: FeedSource
    )