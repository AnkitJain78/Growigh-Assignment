package com.example.assignment.domain.model

import com.example.assignment.data.remote.dto.Urls
import com.example.assignment.data.remote.dto.User
import kotlin.random.Random

data class Feed(
    val id: String?,
    val alt_description: String?,
    val description: String?,
    var isLiked: Boolean = false,
    var likes: Int,
    val urls: Urls,
    val user: User,
    val comments: Int = Random.nextInt(2, 100)
)