package com.rammdakk.recharge.feature.profile.domain

import com.rammdakk.recharge.feature.profile.models.data.ProfileInfo


interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileInfo>

    suspend fun updateProfile(profile: ProfileInfo): Result<Unit>
}