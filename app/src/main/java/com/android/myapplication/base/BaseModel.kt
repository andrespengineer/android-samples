package com.android.myapplication.base

import java.util.*

interface BaseModel {
    val objectId: String?
        get() = UUID.randomUUID().toString()
}