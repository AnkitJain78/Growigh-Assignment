package com.example.assignment.di

import com.example.assignment.data.repository.FeedRepositoryImpl
import com.example.assignment.domain.repository.FeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFeedRepository(
        FeedRepositoryImpl: FeedRepositoryImpl
    ): FeedRepository
}