package com.alexeymerov.reddittesttask.data.repository

import android.arch.lifecycle.LiveData
import com.alexeymerov.reddittesttask.data.database.RedditDatabase
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.data.repository.contracts.IPostRepository
import com.alexeymerov.reddittesttask.data.server.ServerCommunicator
import com.alexeymerov.reddittesttask.data.server.pojo.response.PostsResponse


class PostRepository(private val mServerCommunicator: ServerCommunicator,
                     private val mDatabase: RedditDatabase
) : IPostRepository() {

    override fun getAllLive(): LiveData<List<PostEntity>> {
        requestPosts()
        return mDatabase.postDao().getAllLive()
    }

    private fun requestPosts(lastPostName: String? = null) {
        val single = when (lastPostName) {
            null -> mServerCommunicator.getPosts()
            else -> mServerCommunicator.getPosts(lastPostName)
        }

        single
                .map { parseRawData(it) }
                .doAfterSuccess { mDatabase.postDao().addAll(it) }
                .compose(singleTransformer())
                .subscribe()
    }

    private fun parseRawData(response: PostsResponse): List<PostEntity> {
        val resultList = mutableListOf<PostEntity>()
        response.data?.children
                ?.takeIf { it.isNotEmpty() }
                ?.filter { it.postEntity != null }
                ?.forEach { resultList.add(it.postEntity!!) }
        return resultList
    }

    override fun getAll() = mDatabase.postDao().getAll()

}