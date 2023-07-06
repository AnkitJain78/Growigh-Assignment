package com.example.assignment.domain.repository

import com.example.assignment.domain.model.Feed
import com.example.assignment.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    suspend fun getFeeds(page:Int): Resource<List<Feed>>?
}