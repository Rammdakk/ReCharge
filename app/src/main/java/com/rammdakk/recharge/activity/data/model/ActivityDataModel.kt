package com.rammdakk.recharge.activity.data.model

data class ActivityExtendedDataModel(
    val id: Int,
    val name: String,
    val imagePath: String?,
    val adminPhoneWA: String?,
    val adminTgUsername: String?,
    val locationName: String,
    val locationAddress: String,
    val activityDescription: String?,
    val warning: String?,
    val cancellationMessage: String?
)

