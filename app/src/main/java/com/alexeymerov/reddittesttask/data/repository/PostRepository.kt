package com.alexeymerov.reddittesttask.data.repository

import android.arch.lifecycle.LiveData
import com.alexeymerov.reddittesttask.data.database.RedditDatabase
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.data.repository.contracts.IPostRepository
import com.alexeymerov.reddittesttask.data.server.ServerCommunicator
import com.alexeymerov.reddittesttask.data.server.pojo.response.PostsResponse
import org.jetbrains.anko.doAsync


class PostRepository(private val serverCommunicator: ServerCommunicator,
                     private val database: RedditDatabase
) : IPostRepository() {

    override fun getAllLive(): LiveData<List<PostEntity>> {
        requestPosts()
        return database.postDao().getAllLive()
    }

    private fun requestPosts(lastPostName: String? = null) {
        val single = when (lastPostName) {
            null -> serverCommunicator.getPosts()
            else -> serverCommunicator.getPosts(lastPostName)
        }

        single
                .map { parseRawData(it) }
                .doAfterSuccess { database.postDao().add(it) }
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

    override fun updatePosts() = requestPosts()

    override fun clearPostsBefore(millis: Long) {
        doAsync {
            synchronized(this) {
                database.postDao().clearPostsBefore(millis.toDouble())
            }
        }
    }
}