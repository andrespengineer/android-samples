package com.social.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var id: Long,
                     val thumbnail: String,
                     val name: String,
                     val key: String) : Parcelable
{
    companion object {
        const val USER = "USER"
    }
}