package com.alexeymerov.reddittesttask.koin

import android.content.Context
import androidx.room.Room
import com.alexeymerov.reddittesttask.data.database.RedditDatabase
import com.alexeymerov.reddittesttask.data.repository.PostRepository
import com.alexeymerov.reddittesttask.data.repository.contracts.IPostRepository
import com.alexeymerov.reddittesttask.data.server.ServerCommunicator
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val repositoryModule: Module = module {
    single { provideSharedPrefs(androidApplication().applicationContext) }
    single { provideDatabase(androidApplication().applicationContext) }
    single { provideServerCommunicator() }

    single { PostRepository(get(), get()) as IPostRepository }
}

private fun provideDatabase(context: Context) = Room
        .databaseBuilder(context, RedditDatabase::class.java, "reddit-database")
        .build()

private fun provideSharedPrefs(context: Context) = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
private fun provideServerCommunicator() = ServerCommunicator()


