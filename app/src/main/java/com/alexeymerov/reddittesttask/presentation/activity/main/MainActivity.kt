package com.alexeymerov.reddittesttask.presentation.activity.main

import android.arch.lifecycle.Observer
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alexeymerov.reddittesttask.R
import com.alexeymerov.reddittesttask.WEB_URL
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.domain.contract.IPostsViewModel
import com.alexeymerov.reddittesttask.presentation.adapter.recycler.PostsRecyclerAdapter
import com.alexeymerov.reddittesttask.presentation.base.BaseActivity
import com.alexeymerov.reddittesttask.utils.EndlessRecyclerViewScrollListener
import com.alexeymerov.reddittesttask.utils.extensions.isNetworkConnected
import com.alexeymerov.reddittesttask.utils.extensions.isVisible
import com.alexeymerov.reddittesttask.utils.extensions.makeVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_no_internet_panel.*
import org.koin.android.architecture.ext.viewModel


class MainActivity : BaseActivity() {

    private val viewModel by viewModel<IPostsViewModel>()
    private val recyclerAdapter: PostsRecyclerAdapter by lazy { initRecyclerAdapter() }
    private val linerLayoutManager by lazy { initLayoutManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setNoIntenetView(noInternetTextView)
        initViews()
        clearOldData()
    }

    private fun clearOldData() = viewModel.clearOldPosts()

    private fun initViews() = initItemsList()

    private fun initLayoutManager() = LinearLayoutManager(this).apply {
        isSmoothScrollbarEnabled = true
        isMeasurementCacheEnabled = true
        isItemPrefetchEnabled = true
    }

    private fun initRecyclerAdapter(): PostsRecyclerAdapter = PostsRecyclerAdapter(this).apply {
        onItemClick = { openLink(WEB_URL + it.permalink) }
    }

    private fun openLink(url: String) = CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(url))

    private fun initItemsList() {
        initRecyclerView()
        posts_swipe_view.setOnRefreshListener { viewModel.updatePosts() }
        viewModel.getPostsLive().observe(this, Observer { it?.setOrUpdateItems() })
    }

    private fun List<PostEntity>.setOrUpdateItems() {
        empty_state_view.makeVisible(isEmpty())
        posts_recycler_view.makeVisible(isNotEmpty())
        if (recycler_bottom_progress_bar.isVisible()) recycler_bottom_progress_bar.makeVisible(false)
        if (posts_swipe_view.isRefreshing) posts_swipe_view.isRefreshing = false
        recyclerAdapter.items = this
    }

    override fun onInternetStateChanged(isConnected: Boolean) {
        super.onInternetStateChanged(isConnected)
        posts_swipe_view.isEnabled = isConnected
        if (isConnected) viewModel.updatePosts()
    }

    private fun initRecyclerView() {
        with(posts_recycler_view) {
            layoutManager = linerLayoutManager
            adapter = recyclerAdapter
            addOnScrollListener(object : EndlessRecyclerViewScrollListener(linerLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    isNetworkConnected {
                        if (it) {
                            viewModel.loadNextPosts(totalItemsCount)
                            recycler_bottom_progress_bar.makeVisible()
                        }
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
