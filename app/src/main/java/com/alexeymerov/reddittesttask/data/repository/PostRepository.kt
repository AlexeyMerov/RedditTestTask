package com.alexeymerov.reddittesttask.data.repository

import androidx.lifecycle.LiveData
import com.alexeymerov.reddittesttask.data.database.RedditDatabase
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.data.repository.contracts.IPostRepository
import com.alexeymerov.reddittesttask.data.server.ServerCommunicator
import com.alexeymerov.reddittesttask.data.server.pojo.response.PostsResponse
import io.reactivex.Single


class PostRepository(private val serverCommunicator: ServerCommunicator,
                     private val database: RedditDatabase
) : IPostRepository() {

    private val liveData by lazy { database.postDao().getAllLive() }

    override fun getAllLive(): LiveData<List<PostEntity>> {
        requestPosts()
        return liveData
    }

    private fun requestPosts(lastPostName: String? = null) {
        when (lastPostName) {
            null -> serverCommunicator.getPosts()
            else -> serverCommunicator.getPosts(lastPostName)
        }.proceedRequest()
    }

    private fun Single<PostsResponse>.proceedRequest() {
        this.map { parseRawData(it) }
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
        Single
                .just(millis / 1000)
                .doOnSuccess { database.postDao().clearPostsBefore(it) }
                .compose(singleTransformer())
                .subscribe()
    }

    override fun loadNextPosts(lastPostPosition: Int) {
        Single.just(lastPostPosition)
                .map { liveData.value?.get(lastPostPosition) }
                .map { it.postName }
                .flatMap { serverCommunicator.getPosts(it) }
                .proceedRequest()
    }
}