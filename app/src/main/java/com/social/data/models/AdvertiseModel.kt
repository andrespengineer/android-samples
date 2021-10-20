package com.social.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdvertiseModel(
        var id: Long,
        val image: String,
        val key: String) : Parcelable
{
    companion object {
        const val ADVERTISE = "ADVERTISE"
    }
}