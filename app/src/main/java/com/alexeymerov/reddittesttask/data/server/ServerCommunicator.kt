package com.alexeymerov.reddittesttask.data.server

import com.alexeymerov.reddittesttask.API_URL
import com.alexeymerov.reddittesttask.BuildConfig
import com.alexeymerov.reddittesttask.data.server.pojo.response.PostsResponse
import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Single
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ServerCommunicator {

    private val apiService: ApiService

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
                .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
                    .addNetworkInterceptor(StethoInterceptor())
        }

        val retrofitBuilder = Retrofit.Builder()
                .client(okHttpClientBuilder.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        val retrofit = retrofitBuilder.baseUrl(API_URL).build()
        apiService = retrofit.create<ApiService>(ApiService::class.java)
    }

    fun getPosts(): Single<PostsResponse> = apiService.getPosts()

    fun getPosts(lastPostName: String): Single<PostsResponse> = apiService.getPosts(lastPostName)


}