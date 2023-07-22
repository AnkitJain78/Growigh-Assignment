package com.example.assignment.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assignment.data.remote.dto.Urls
import com.example.assignment.data.remote.dto.User
import com.example.assignment.domain.model.Feed

@Entity(tableName = "feed_entity")
data class FeedEntity(
    @PrimaryKey val id: String,
    val alt_description: String?,
    val description: String?,
    val isLiked: Boolean,
    val likes: Int,
    @Embedded val urls: Urls,
    @Embedded val user: User,
    val comments: Int
)

fun FeedEntity.toFeed(): Feed {
    return Feed(
        id,
        alt_description,
        description,
        isLiked,
        likes,
        urls,
        user,
        comments
    )
}