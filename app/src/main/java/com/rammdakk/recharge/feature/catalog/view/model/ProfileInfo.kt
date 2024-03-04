package com.rammdakk.recharge.feature.catalog.view.model

import com.rammdakk.recharge.feature.catalog.data.model.ProfileDataModel

data class ProfileInfo(
    val photoPath: String,
    val name: String
)

fun ProfileDataModel.convertToProfileInfo(): ProfileInfo {
    return ProfileInfo(name = this.name, photoPath = this.photoPath)
}