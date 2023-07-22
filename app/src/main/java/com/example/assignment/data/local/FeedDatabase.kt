package com.example.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FeedEntity::class], version = 1)
abstract class FeedDatabase : RoomDatabase() {
    abstract val dao: FeedDao
}