package com.alexeymerov.reddittesttask

import android.app.Application
import com.alexeymerov.reddittesttask.koin.repositoryModule
import com.alexeymerov.reddittesttask.koin.viewModelModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin(this, listOf(viewModelModule, repositoryModule))

    }
}