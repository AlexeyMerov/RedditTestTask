package com.alexeymerov.reddittesttask.data.server.pojo.response

import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.squareup.moshi.Json

data class Data(

        @Json(name = "children")
        val children: List<ChildrenItem>? = null
)

data class ChildrenItem(

        @Json(name = "data")
        val postEntity: PostEntity? = null
)