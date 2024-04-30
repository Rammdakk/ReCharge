package com.rammdakk.recharge.feature.profile.domain

import com.rammdakk.recharge.feature.profile.models.data.ProfileInfo
import com.rammdakk.recharge.feature.profile.models.data.ShortProfileInfo


interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileInfo>

    suspend fun getProfileShortInfo(): Result<ShortProfileInfo>

    suspend fun updateProfile(profile: ProfileInfo): Result<Unit>
}