package com.alexeymerov.reddittesttask.koin

import com.alexeymerov.reddittesttask.domain.PostsViewModel
import com.alexeymerov.reddittesttask.domain.contract.IPostsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val viewModelModule: Module = module {
    viewModel { PostsViewModel(get(), get()) as IPostsViewModel }
}
