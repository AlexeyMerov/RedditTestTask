package com.alexeymerov.reddittesttask.data.server

import com.alexeymerov.reddittesttask.data.server.pojo.response.PostsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("r/popular/top/.json")
    fun getPosts(@Query("count") count: Int = 25): Single<PostsResponse>

    @GET("r/popular/top/.json")
    fun getPosts(@Query("after") lastPostName: String,
                 @Query("count") count: Int = 25): Single<PostsResponse>

}