package com.social.presentation.base

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import com.social.presentation.controls.SingleImageLoaderFactory

@HiltAndroidApp
class BaseApplication : Application(), SingleImageLoaderFactory {

    override lateinit var imageLoaderContext: Context

    override fun onCreate() {
        super.onCreate()
        imageLoaderContext = this
    }
}

