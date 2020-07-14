package com.alexeymerov.reddittesttask.data.server.pojo.response

import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostsResponse(
        @Json(name = "data")
        val data: Data? = null
)

@JsonClass(generateAdapter = true)
data class Data(
        @Json(name = "children")
        val children: List<ChildrenItem>? = null
)

@JsonClass(generateAdapter = true)
data class ChildrenItem(
        @Json(name = "data")
        val postEntity: PostEntity? = null
)