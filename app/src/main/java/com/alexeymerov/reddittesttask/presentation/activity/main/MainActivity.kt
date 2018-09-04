package com.alexeymerov.reddittesttask.presentation.activity.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alexeymerov.reddittesttask.R
import com.alexeymerov.reddittesttask.data.database.entity.PostEntity
import com.alexeymerov.reddittesttask.domain.contract.IPostsViewModel
import com.alexeymerov.reddittesttask.presentation.adapter.recycler.PostsRecyclerAdapter
import com.alexeymerov.reddittesttask.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_no_internet_panel.*
import org.koin.android.architecture.ext.viewModel


class MainActivity : BaseActivity() {

    private val mViewModel by viewModel<IPostsViewModel>()
    private val mChatsRecyclerAdapter: PostsRecyclerAdapter by lazy { initRecyclerAdapter() }
    private val mLinerLayoutManager by lazy { initLayoutManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setNoIntenetView(noInternetTextView)
        initViews()
    }

    private fun initViews() {
        initChatList()
    }

    private fun initLayoutManager() = LinearLayoutManager(this).apply {
        isSmoothScrollbarEnabled = true
        isMeasurementCacheEnabled = true
        isItemPrefetchEnabled = true
    }

    private fun initRecyclerAdapter(): PostsRecyclerAdapter = PostsRecyclerAdapter(this).apply {
        //        setHasStableIds(true)
//        onItemClick = { startChatActivity(it) }
//        registerAdapterDataObserver(getAdapterDataObserver())
    }

    private fun initChatList() {
        initRecyclerView()
        mViewModel.getPostsLive().apply {
            value?.setOrUpdateItems()
            observe(this@MainActivity, Observer { it?.setOrUpdateItems() })
        }
    }

    private fun List<PostEntity>.setOrUpdateItems() {
        mChatsRecyclerAdapter.items = this
//        emptyView.makeVisible(isEmpty() && !getShared(STATE_NEED_SHOW_FAB, true))
    }

    private fun initRecyclerView() {
        with(posts_recycler_view) {
            //            setItemViewCacheSize(20)
//            addItemDecoration(DividerItemDecoration(this@MainActivity, VERTICAL).apply { setDrawable(getDrawable(R.drawable.shape_divider)) })
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
            layoutManager = mLinerLayoutManager
            adapter = mChatsRecyclerAdapter
        }
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
//        overridePendingTransition(R.anim.slide_in_from_right, android.R.anim.fade_out)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
