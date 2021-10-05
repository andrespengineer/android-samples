package com.android.myapplication.util

import com.bumptech.glide.load.Key
import kotlinx.coroutines.*
import java.security.MessageDigest

class MyKeySignature(private val key: Long) : Key {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(key.toString().toByteArray())
    }


}