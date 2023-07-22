package com.example.assignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FeedDao {
    @Query("SELECT * FROM feed_entity")
    suspend fun getFeeds(): List<FeedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeeds(feedList: List<FeedEntity>)

    @Query("DELETE FROM feed_entity")
    suspend fun clearFeeds()

    @Query("UPDATE feed_entity SET likes = :likes, isLiked = :isLiked WHERE id = :id")
    suspend fun updateFeed(id: String, likes: Int, isLiked: Boolean)
}