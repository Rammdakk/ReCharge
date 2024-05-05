package com.rammdakk.recharge.feature.catalog.view.model

import com.rammdakk.recharge.feature.profile.data.models.ProfileHeaderDataModel

data class ProfileInfo(
    val photoPath: String?,
    val name: String?
)

fun ProfileHeaderDataModel.convertToProfileInfo(): ProfileInfo {
    return ProfileInfo(name = this.name.takeIf { !it.isNullOrEmpty() }, photoPath = this.photoPath)
}