package com.android.myapplication.data.models

import com.android.myapplication.base.BaseModel
import com.android.myapplication.util.MyStringUtil
import java.io.Serializable

data class Post(val userId: Long,
                val postId: Long,
                val username: String,
                val profilePicture: String?,
                val postPicture: String?,
                val postLocation: String?,
                val postDescription: String?,
                private var postLikes: String?, var liked: Boolean) : Serializable, BaseModel {

    fun getPostLikes(): String {
        return MyStringUtil.formatNumberToDecimal(postLikes)
    }

    fun setPostLikes(postLikes: String?) {
        this.postLikes = postLikes
    }
}