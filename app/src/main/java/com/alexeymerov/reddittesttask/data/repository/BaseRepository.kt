package com.alexeymerov.reddittesttask.data.repository

import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

abstract class BaseRepository {

    companion object {
        private const val DEFAULT_TIMEOUT = 10L // seconds
        private const val DEFAULT_RETRY_ATTEMPTS: Long = 4
    }

    protected fun <T> singleTransformer(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .timeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retry(DEFAULT_RETRY_ATTEMPTS)
    }
}