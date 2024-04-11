package com.rammdakk.recharge.feature.activity.view.model

import com.rammdakk.recharge.feature.activity.data.model.UserBookingDataModel

data class UserBookingInfo(
    val userName: String,
    val phone: String,
    val email: String,
    val reserveCount: Int,
)

fun UserBookingInfo.covertToDataModel(): UserBookingDataModel =
    UserBookingDataModel(userName, phone, email, reserveCount)