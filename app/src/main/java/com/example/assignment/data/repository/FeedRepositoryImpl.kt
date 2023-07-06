package com.example.assignment.data.repository

import android.util.Log
import com.example.assignment.data.mapper.toFeed
import com.example.assignment.data.remote.FeedApi
import com.example.assignment.data.remote.dto.FeedResponse
import com.example.assignment.domain.model.Feed
import com.example.assignment.domain.repository.FeedRepository
import com.example.assignment.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepositoryImpl @Inject constructor(
    val remoteApi: FeedApi
): FeedRepository {
    var returnVal: Resource<List<Feed>>? = null

    override suspend fun getFeeds(page:Int): Resource<List<Feed>>? {
        return try {
            val result = remoteApi.getFeeds(page)
            val feedList = result.body()?.articles?.map {
                it.toFeed()
            }
            Resource.Success(feedList)
        }
        catch (e: IOException){
            return null
        }
        catch(e: HttpException) {
            return null
        }
    }
}