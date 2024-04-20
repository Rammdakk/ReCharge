package com.rammdakk.recharge.base.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.format() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(this)