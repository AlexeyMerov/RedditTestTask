package com.alexeymerov.reddittesttask.presentation.adapter.recycler

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.alexeymerov.reddittesttask.R
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.presentation.base.BaseRecyclerAdapter
import com.alexeymerov.reddittesttask.presentation.base.BaseViewHolder
import com.alexeymerov.reddittesttask.utils.*
import com.alexeymerov.reddittesttask.utils.extensions.inflate
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_post.*
import java.util.*

class PostsRecyclerAdapter(context: Context) : BaseRecyclerAdapter<PostEntity, PostsRecyclerAdapter.PostViewHolder>() {

    private val requestManager: RequestManager = Glide.with(context)
            .applyDefaultRequestOptions(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.reddit_icon)
                    .error(R.drawable.reddit_icon))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent.inflate(R.layout.item_post))

    override fun compareItems(old: PostEntity, new: PostEntity) = old == new

    inner class PostViewHolder(containerView: View) : BaseViewHolder<PostEntity>(containerView) {

        override fun bind(currentItem: PostEntity) {
            with(currentItem) {
                requestManager.load(thumbnailUrl).into(post_thumb_image_view)
                post_title_text_view.text = title

                setScore()
                post_subreddit_text_view.text = String.format("Community: %s", subReddit)

                post_author_text_view.text = authorName
                setDate()
                post_comments_text_view.text = numComments.toString()
            }
        }

        private fun PostEntity.setScore() {
            post_rating_text_view.text = when {
                score < 1000 -> score.toString()
                else -> {
                    val scoreString = (score / 1000).toString()
                    when {
                        scoreString.contains(".") -> scoreString.substring(0, scoreString.indexOf("."))
                        else -> scoreString
                    }.plus("k")
                }
            }
        }

        private fun PostEntity.setDate() {
            val currentDate = Date()
            val postDate = Date(createdDate.toLong() * 1000)

            var timeDifference = differenceBetweenInMonths(postDate, currentDate)
            var postfix = "months"

            if (timeDifference == 0) {
                timeDifference = differenceBetweenInDays(postDate, currentDate)
                postfix = "days"

                if (timeDifference == 0) {
                    timeDifference = differenceBetweenInHours(postDate, currentDate)
                    postfix = "hours"

                    if (timeDifference == 0) {
                        timeDifference = differenceBetweenInMinutes(postDate, currentDate)
                        postfix = "minutes"

                        if (timeDifference == 0) {
                            timeDifference = differenceBetweenInSeconds(postDate, currentDate)
                            postfix = "seconds"
                        }
                    }
                }
            }

            post_date_text_view.text = String.format("%d %s ago", timeDifference, postfix)
        }

    }
}