package com.rammdakk.recharge.feature.profile.domain

import com.rammdakk.recharge.feature.profile.data.model.ProfileInfo


interface ProfileRepository {
    suspend fun getProfile(): ProfileInfo

    suspend fun updateProfile(profile: ProfileInfo)
}