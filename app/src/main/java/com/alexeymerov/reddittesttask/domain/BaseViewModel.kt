package com.alexeymerov.reddittesttask.domain

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.alexeymerov.reddittesttask.App

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    fun getContext() = getApplication<App>()

    fun getString(@StringRes id: Int): String = getContext().getString(id)

}