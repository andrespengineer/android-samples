package com.android.myapplication.util

import java.text.DecimalFormat

object MyStringUtil {
    fun formatNumberToDecimal(number: String?): String {
        if (number == null) return 0.toString()
        val likes = number.toInt()
        val decimalFormat = DecimalFormat("#,###")
        decimalFormat.isGroupingUsed = true
        decimalFormat.groupingSize = 4
        return decimalFormat.format(likes.toLong())
    }
}