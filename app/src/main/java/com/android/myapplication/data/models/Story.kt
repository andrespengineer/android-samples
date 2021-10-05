package com.android.myapplication.data.models

import com.android.myapplication.base.BaseModel
import java.io.Serializable

data class Story(var isHeader: Boolean,
                 val userId: Long,
                 val username: String?,
                 val profilePicture: String?) : Serializable, BaseModel