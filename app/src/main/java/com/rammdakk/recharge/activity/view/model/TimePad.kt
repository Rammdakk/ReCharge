package com.rammdakk.recharge.activity.view.model

import com.rammdakk.recharge.activity.data.model.TimePadDataModel
import java.util.Date

data class TimePad(
    val id: Int,
    val price: Double,
    val startTime: Date,
    val endTime: Date,
    val onClick: (Int) -> Unit
)

fun TimePadDataModel.covertToTimePad(onClick: (Int) -> Unit): TimePad =
    TimePad(
        id = id,
        price = price,
        startTime = startTime,
        endTime = endTime,
        onClick = onClick
    )