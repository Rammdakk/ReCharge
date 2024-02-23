package com.rammdakk.recharge.profile.domain

import com.rammdakk.recharge.profile.data.model.ProfileInfo


interface ProfileRepository {
    suspend fun getProfile(): ProfileInfo

    suspend fun updateProfile(profile: ProfileInfo)
}