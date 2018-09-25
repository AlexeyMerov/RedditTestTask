package com.alexeymerov.reddittesttask.domain.contract

import android.app.Application
import androidx.lifecycle.LiveData
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.domain.BaseViewModel

abstract class IPostsViewModel(application: Application) : BaseViewModel(application) {

    abstract fun getPostsLive(): LiveData<List<PostEntity>>

    abstract fun updatePosts()

    abstract fun clearOldPosts()

    abstract fun loadNextPosts(totalItemsCount: Int)

}