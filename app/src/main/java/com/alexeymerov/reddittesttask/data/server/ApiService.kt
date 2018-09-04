package com.alexeymerov.reddittesttask.data.server

import com.alexeymerov.reddittesttask.data.server.pojo.response.PostsResponse
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("r/popular/top/.json")
    fun getPosts(@Query("count") count: Int = 15): Single<PostsResponse>

    @POST("r/popular/top/.json")
    fun getPosts(@Query("after") lastPostName: String,
                 @Query("count") count: Int = 15): Single<PostsResponse>

}