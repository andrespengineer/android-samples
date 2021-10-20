package com.social.presentation.controls

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PreLoadLayoutManager @JvmOverloads constructor(context: Context, var extraLayoutSpace: Int, orientation: Int = RecyclerView.VERTICAL, reverseLayout: Boolean = false) : LinearLayoutManager(context, orientation, reverseLayout)
{
     override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return if (extraLayoutSpace > 0) {
            extraLayoutSpace
        } else DEFAULT_EXTRA_LAYOUT_SPACE
    }
    companion object {
        private const val DEFAULT_EXTRA_LAYOUT_SPACE = 1500
    }
}