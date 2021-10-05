package com.android.myapplication.ui.common

interface CommonAdapterImpl<in T> {
    fun updateAdapterData(items: T) { /* Default impl */ }
}