package com.rammdakk.recharge.base.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun String.toCalendar(): Calendar? {
    val aTime = "2017-10-25T11:39:00+09:00"
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    return runCatching {
        val cal: Calendar = Calendar.getInstance()
        sdf.parse(aTime)?.let { cal.time = it } ?: return null
        cal
    }.getOrNull()
}