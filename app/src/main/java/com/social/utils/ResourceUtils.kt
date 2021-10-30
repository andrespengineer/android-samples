package com.social.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.google.android.material.color.MaterialColors

object ResourceUtils {

    fun getColorFromAttr(context : Context, attrColorId: Int) : Int {
        return MaterialColors.getColor(context, attrColorId, Color.BLACK)
    }
}