package com.android.myapplication.util

import com.android.myapplication.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object MyGlideRequestOptions {

    val storiesRequestOption: RequestOptions
        get() = RequestOptions()
            .error(R.drawable.ic_no_camera)
            .placeholder(R.color.colorPrimary)
            .dontAnimate()
            .fitCenter()
            .override(200, 200)

    val profileRequestOption: RequestOptions
        get() = RequestOptions()
            .error(R.drawable.ic_no_camera)
            .placeholder(R.color.background)

    val postRequestOption: RequestOptions
        get() = RequestOptions()
            .error(R.drawable.ic_warning)
            .fitCenter()
            .override(400, 400)
}