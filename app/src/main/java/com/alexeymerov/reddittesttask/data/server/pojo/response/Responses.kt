package com.alexeymerov.reddittesttask.data.server.pojo.response

import com.squareup.moshi.Json

data class PostsResponse(

        @Json(name = "data")
        val data: Data? = null

)