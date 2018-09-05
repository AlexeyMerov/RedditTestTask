package com.alexeymerov.reddittesttask.presentation.adapter.recycler

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.alexeymerov.reddittesttask.R
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.presentation.base.BaseRecyclerAdapter
import com.alexeymerov.reddittesttask.presentation.base.BaseViewHolder
import com.alexeymerov.reddittesttask.utils.*
import com.alexeymerov.reddittesttask.utils.extensions.dpToPx
import com.alexeymerov.reddittesttask.utils.extensions.inflate
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_post.*
import java.util.*

class PostsRecyclerAdapter(val context: Context) : BaseRecyclerAdapter<PostEntity, PostsRecyclerAdapter.PostViewHolder>() {

    private enum class ChangeType {
        COMMENTS, LIKES
    }

    private val requestOptions = RequestOptions()
            .placeholder(R.drawable.reddit_icon)
            .error(R.drawable.reddit_icon)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transforms(CenterCrop(), RoundedCorners(4.dpToPx()))

    private val requestManager: RequestManager = Glide.with(context).setDefaultRequestOptions(requestOptions)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent.inflate(R.layout.item_post))

    override fun compareItems(old: PostEntity, new: PostEntity) = old.id == new.id

    override fun compareContent(old: PostEntity, new: PostEntity): Any? {
        val changesList = mutableListOf<ChangeType>()

        if (old.numComments != new.numComments) changesList.add(ChangeType.COMMENTS)
        if (old.score != new.score) changesList.add(ChangeType.LIKES)

        return changesList
    }

    override fun proceedPayloads(payloads: MutableList<Any>, holder: PostViewHolder, position: Int) {
        payloads
                .filter { it is ChangeType }
                .forEach {
                    holder.apply {
                        if (it == ChangeType.COMMENTS) items[position].setComments()
                        else if (it == ChangeType.LIKES) items[position].setScore()
                    }
                }
    }

    inner class PostViewHolder(containerView: View) : BaseViewHolder<PostEntity>(containerView) {

        override fun bind(currentItem: PostEntity) {
            with(currentItem) {
                loadImage()
                post_title_text_view.text = title
                post_subreddit_text_view.text = subReddit
                post_author_text_view.text = authorName
                setScore()
                setDate()
                setComments()
            }
        }

        fun PostEntity.loadImage() {
            requestManager
                    .load(thumbnailUrl)
                    .into(post_thumb_image_view)
        }

        fun PostEntity.setComments() {
            post_comments_text_view.text = numComments.toString()
        }

        fun PostEntity.setScore() {
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
            var postfix = context.resources.getQuantityString(R.plurals.month_plural, timeDifference)

            if (timeDifference == 0) {
                timeDifference = differenceBetweenInDays(postDate, currentDate)
                postfix = context.resources.getQuantityString(R.plurals.days_plural, timeDifference)

                if (timeDifference == 0) {
                    timeDifference = differenceBetweenInHours(postDate, currentDate)
                    postfix = context.resources.getQuantityString(R.plurals.hours_plural, timeDifference)

                    if (timeDifference == 0) {
                        timeDifference = differenceBetweenInMinutes(postDate, currentDate)
                        postfix = context.resources.getQuantityString(R.plurals.minutes_plural, timeDifference)

                        if (timeDifference == 0) {
                            timeDifference = differenceBetweenInSeconds(postDate, currentDate)
                            postfix = context.resources.getQuantityString(R.plurals.seconds_plural, timeDifference)
                        }
                    }
                }
            }

            post_date_text_view.text = String.format("%d %s ago", timeDifference, postfix)
        }

    }
}