package com.rammdakk.recharge.feature.profile.domain

import com.rammdakk.recharge.feature.profile.data.models.ProfileHeaderDataModel
import com.rammdakk.recharge.feature.profile.data.models.ProfileInfo
import com.rammdakk.recharge.feature.profile.data.models.ShortProfileInfo


interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileInfo>

    suspend fun getProfileHeaderInfo(): Result<ProfileHeaderDataModel>

    suspend fun getProfileShortInfo(forceUpdate: Boolean = false): Result<ShortProfileInfo>

    suspend fun updateProfile(profile: ProfileInfo): Result<Unit>
}