package com.alexeymerov.reddittesttask.data.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.alexeymerov.reddittesttask.data.database.dao.PostDAO
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = PostDAO.TABLE_NAME)
@Parcelize
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
        val createdDate: Double,

        @Json(name = "score")
        val score: Int = 0,

        @Json(name = "num_comments")
        val numComments: Int = 0,

        @Json(name = "author")
        val authorName: String,

        @Json(name = "name")
        val postName: String,

        @Json(name = "permalink")
        val permalink: String,

        @Json(name = "url")
        val imageUrl: String? = null

) : Parcelable