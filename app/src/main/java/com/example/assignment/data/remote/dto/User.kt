package com.example.assignment.data.remote.dto

import androidx.room.Embedded

data class User(
    val name: String,
    @Embedded val profile_image: ProfileImageX,
    val username: String
)