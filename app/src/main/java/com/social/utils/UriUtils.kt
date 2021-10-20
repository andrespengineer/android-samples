package com.social.utils

import java.text.SimpleDateFormat
import java.util.*

object UriUtils {
    /**
     * build uri with params
     *
     * @param url
     * @param params
     * @return
     */

    fun generateRandomFileName(): String {
        val sdf = SimpleDateFormat("_yyyy-MM-dd", Locale.getDefault())
        val dateAndTime = sdf.format(Date())
        return UUID.randomUUID().toString() + dateAndTime
    }
}