package com.android.myapplication.base

import android.content.Context
import com.android.myapplication.util.MyGlideRequestOptions
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class BaseGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.apply {
            setLogRequestOrigins(false)
        }
    }
}