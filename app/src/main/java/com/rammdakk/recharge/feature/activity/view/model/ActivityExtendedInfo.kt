package com.rammdakk.recharge.feature.activity.view.model

import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel

data class ActivityExtendedInfo(
    val name: String,
    val imagePath: String?,
    val admin: AdminInfo?,
    val organizationName: String,
    val address: String,
    val activityDescription: String?,
    val warning: String?,
    val cancellationMessage: String?,
)

data class AdminInfo(
    val whatsApp: String?,
    val telegram: String?,
)

fun ActivityExtendedDataModel.convertToActivityInfo(): ActivityExtendedInfo? {
    return ActivityExtendedInfo(
        name = this.name ?: "",
        imagePath = this.imagePath,
        admin = if (this.adminPhoneWA == null && this.adminTgUsername == null) null
        else AdminInfo(this.adminPhoneWA, this.adminTgUsername?.removePrefix("@")),
        organizationName = this.locationName ?: "",
        address = this.locationAddress ?: "",
        activityDescription = this.activityDescription,
        warning = this.warning,
        cancellationMessage = this.cancellationMessage,
    )
}