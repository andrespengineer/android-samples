package com.social.presentation.base

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import coil.util.CoilUtils
import com.social.R
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class BaseApplication : Application(), ImageLoaderFactory{

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(null)
                    .build()
            }.memoryCachePolicy(CachePolicy.DISABLED)
            .crossfade(true)
            .placeholder(R.drawable.circular_progressbar)
            .build()
    }

}