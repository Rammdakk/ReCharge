package com.rammdakk.recharge.catalog.view.model

import com.rammdakk.recharge.catalog.data.model.ProfileDataModel

data class ProfileInfo(
    val photoPath: String,
    val name: String
)

fun ProfileDataModel.convertToProfileInfo(): ProfileInfo {
    return ProfileInfo(name = this.name, photoPath = this.photoPath)
}