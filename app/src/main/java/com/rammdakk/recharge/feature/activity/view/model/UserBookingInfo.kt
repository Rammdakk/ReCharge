package com.rammdakk.recharge.feature.activity.view.model

import com.rammdakk.recharge.feature.activity.data.model.UserBookingDataModel

data class UserBookingInfo(
    val userName: String,
    val phone: String,
    val email: String,
    val reserveCount: Int,
    val calendarInfo: CalendarActivityInfo
)

data class CalendarActivityInfo(
    val activityName: String,
    val startDate: Long,
    val endTime: Long,
    val locationName: String,
)

fun UserBookingInfo.covertToDataModel(): UserBookingDataModel =
    UserBookingDataModel(userName, phone, email, reserveCount)