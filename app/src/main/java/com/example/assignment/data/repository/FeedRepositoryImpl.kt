package com.example.assignment.data.repository

import com.example.assignment.data.local.FeedDatabase
import com.example.assignment.data.local.FeedEntity
import com.example.assignment.data.local.toFeed
import com.example.assignment.data.remote.FeedApi
import com.example.assignment.domain.model.Feed
import com.example.assignment.domain.repository.FeedRepository
import com.example.assignment.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FeedRepositoryImpl @Inject constructor(
    private val remoteApi: FeedApi,
    private val localDB: FeedDatabase
) : FeedRepository {
    private val feedDao = localDB.dao

    override suspend fun getFeeds(page: Int, fetchFromRemote: Boolean): Resource<List<Feed>>? {

        val data = feedDao.getFeeds()
        val isLocalEmpty = data.isEmpty()
        val shouldLoadFromLocal = !isLocalEmpty && !fetchFromRemote
        if (shouldLoadFromLocal) {
            val feeds = data.map {
                it.toFeed()
            }
            return Resource.Success(feeds)
        }
        return try {
            val result = remoteApi.getFeeds(page)
            val feedEntityList = result.body()?.map {
                with(it) {
                    FeedEntity(
                        id,
                        alt_description,
                        description,
                        isLiked,
                        likes,
                        urls,
                        user,
                        Random.nextInt(1, 50)
                    )
                }
            }
            feedDao.clearFeeds()
            feedEntityList?.let { feedDao.insertFeeds(it) }
            Resource.Success(feedDao.getFeeds().map {
                it.toFeed()
            })
        } catch (e: IOException) {
            return null
        } catch (e: HttpException) {
            return null
        }
    }

    override suspend fun updateFeed(feed: Feed) {
        feedDao.updateFeed(feed.id!!, feed.likes, feed.isLiked)
    }
}