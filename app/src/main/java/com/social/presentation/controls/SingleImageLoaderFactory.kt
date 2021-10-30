package com.social.presentation.controls

import android.content.Context
import android.graphics.drawable.ColorDrawable
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import com.social.R
import com.social.utils.ResourceUtils
import okhttp3.OkHttpClient


interface SingleImageLoaderFactory : ImageLoaderFactory {

    val imageLoaderContext: Context

    // MEMORY CACHE DISABLED FOR TESTING PURPOSES (POSTMAN MOCK SERVER)
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(imageLoaderContext)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(null)
                    .build()
            }
            .memoryCachePolicy(CachePolicy.DISABLED)
            .crossfade(true)
            .build()
    }

}