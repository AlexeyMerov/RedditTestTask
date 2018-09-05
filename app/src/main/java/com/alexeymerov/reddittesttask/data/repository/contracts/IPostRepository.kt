package com.alexeymerov.reddittesttask.data.repository.contracts

import android.arch.lifecycle.LiveData
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.data.repository.BaseRepository
import org.koin.standalone.KoinComponent

abstract class IPostRepository : BaseRepository(), KoinComponent {

    abstract fun getAllLive(): LiveData<List<PostEntity>>

    abstract fun updatePosts()

    abstract fun clearPostsBefore(millis: Long)

    abstract fun loadNextPosts(lastPostPosition: Int)

}