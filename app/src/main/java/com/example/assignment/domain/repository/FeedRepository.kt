package com.example.assignment.domain.repository

import com.example.assignment.domain.model.Feed
import com.example.assignment.utils.Resource

interface FeedRepository {
    suspend fun getFeeds(page: Int, fetchFromRemote: Boolean): Resource<List<Feed>>?
    suspend fun updateFeed(feed: Feed)
}