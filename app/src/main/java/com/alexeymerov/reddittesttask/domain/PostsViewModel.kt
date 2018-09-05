package com.alexeymerov.reddittesttask.domain

import android.app.Application
import android.arch.lifecycle.LiveData
import com.alexeymerov.reddittesttask.DAY_MILLIS
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.data.repository.contracts.IPostRepository
import com.alexeymerov.reddittesttask.domain.contract.IPostsViewModel
import java.util.*

class PostsViewModel(application: Application, private val postRepository: IPostRepository) : IPostsViewModel(application) {

    override fun getPostsLive(): LiveData<List<PostEntity>> = postRepository.getAllLive()

    override fun updatePosts() = postRepository.updatePosts()

    override fun clearOldPosts() = postRepository.clearPostsBefore(Date().time - DAY_MILLIS)

    override fun loadNextPosts(totalItemsCount: Int) = postRepository.loadNextPosts(totalItemsCount - 1)
}