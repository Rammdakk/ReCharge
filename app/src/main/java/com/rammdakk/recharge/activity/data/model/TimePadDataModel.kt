package com.rammdakk.recharge.activity.data.model

import java.util.Date

data class TimePadDataModel(
    val id: Int,
    val price: Double,
    val startTime: Date,
    val endTime: Date,
)
