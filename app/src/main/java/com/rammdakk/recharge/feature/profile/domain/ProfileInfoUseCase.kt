package com.rammdakk.recharge.feature.profile.domain

import com.rammdakk.recharge.feature.profile.data.models.ProfileInfo

interface ProfileInfoUseCase {

    suspend fun getProfile(): Result<ProfileInfo>

    suspend fun updateProfile(profile: ProfileInfo): Result<Unit>
}