package com.alexeymerov.reddittesttask.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexeymerov.reddittesttask.data.database.dao.PostDAO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = PostDAO.TABLE_NAME)
@JsonClass(generateAdapter = true)
data class PostEntity(
        @PrimaryKey
        @Json(name = "id")
        val id: String,

        @Json(name = "title")
        val title: String,

        @Json(name = "thumbnail")
        val thumbnailUrl: String? = null,

        @Json(name = "subreddit")
        val subReddit: String,

        @Json(name = "created_utc")
        val createdDate: Long,

        @Json(name = "score")
        val score: Int = 0,

        @Json(name = "num_comments")
        var numComments: Int = 0,

        @Json(name = "author")
        val authorName: String,

        @Json(name = "name")
        val postName: String,

        @Json(name = "permalink")
        val permalink: String,

        @Json(name = "url")
        val imageUrl: String? = null

)