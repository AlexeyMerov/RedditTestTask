package com.alexeymerov.reddittesttask.koin

import com.alexeymerov.reddittesttask.domain.PostsViewModel
import com.alexeymerov.reddittesttask.domain.contract.IPostsViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

val viewModelModule: Module = applicationContext {
    viewModel { PostsViewModel(get(), get()) as IPostsViewModel }
}
