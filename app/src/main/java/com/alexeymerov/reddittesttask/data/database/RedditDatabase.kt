package com.alexeymerov.reddittesttask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexeymerov.reddittesttask.data.database.dao.PostDAO
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class RedditDatabase : RoomDatabase() {

    abstract fun postDao(): PostDAO

}