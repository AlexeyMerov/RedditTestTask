package com.alexeymerov.reddittesttask.presentation.activity.main

import android.arch.lifecycle.Observer
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alexeymerov.reddittesttask.R
import com.alexeymerov.reddittesttask.WEB_URL
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.domain.contract.IPostsViewModel
import com.alexeymerov.reddittesttask.presentation.adapter.recycler.PostsRecyclerAdapter
import com.alexeymerov.reddittesttask.presentation.base.BaseActivity
import com.alexeymerov.reddittesttask.utils.EndlessRecyclerViewScrollListener
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
        viewModel.getPostsLive().apply {
            value?.setOrUpdateItems()
            observe(this@MainActivity, Observer { it?.setOrUpdateItems() })
        }
    }

    private fun List<PostEntity>.setOrUpdateItems() {
        if (posts_swipe_view.isRefreshing) posts_swipe_view.isRefreshing = false
        recyclerAdapter.items = this
//        emptyView.makeVisible(isEmpty() && !getShared(STATE_NEED_SHOW_FAB, true))
    }

    private fun initRecyclerView() {
        with(posts_recycler_view) {
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            layoutManager = linerLayoutManager
            adapter = recyclerAdapter
            addOnScrollListener(object : EndlessRecyclerViewScrollListener(linerLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
//                    progressBar.visibility = View.VISIBLE
//                    if (!isInSearch) viewModel.loadNext(page + 1) //api not allowed send page lower 1
//                    else if (lastQuery != null) viewModel.searchImagesNext(lastQuery!!, page + 1)
                }
            })
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
