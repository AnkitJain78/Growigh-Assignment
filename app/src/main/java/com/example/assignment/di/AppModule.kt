package com.example.assignment.di

import android.app.Application
import androidx.room.Room
import com.example.assignment.data.local.FeedDatabase
import com.example.assignment.data.remote.FeedApi
import com.example.assignment.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFeedApi(authInterceptor: AuthInterceptor): FeedApi {
        return Retrofit.Builder()
            .baseUrl(FeedApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()
            )
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFeedDatabase(app: Application): FeedDatabase {
        return Room.databaseBuilder(
            app,
            FeedDatabase::class.java,
            "feed.db"
        ).build()
    }
}