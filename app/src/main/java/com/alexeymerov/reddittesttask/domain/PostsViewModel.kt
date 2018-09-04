package com.alexeymerov.reddittesttask.domain

import android.app.Application
import android.arch.lifecycle.LiveData
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.data.repository.contracts.IPostRepository
import com.alexeymerov.reddittesttask.domain.contract.IPostsViewModel

class PostsViewModel(application: Application, private val postRepository: IPostRepository) : IPostsViewModel(application) {

    override fun getPostsLive(): LiveData<List<PostEntity>> = postRepository.getAllLive()

}