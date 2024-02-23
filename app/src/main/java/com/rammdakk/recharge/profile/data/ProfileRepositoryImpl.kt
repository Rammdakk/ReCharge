package com.rammdakk.recharge.profile.data

import com.rammdakk.recharge.profile.data.model.Gender
import com.rammdakk.recharge.profile.data.model.ProfileInfo
import com.rammdakk.recharge.profile.domain.ProfileRepository
import kotlinx.coroutines.Dispatchers
import java.util.Date

class ProfileRepositoryImpl(
    dispatchers: Dispatchers
) : ProfileRepository {
    override suspend fun getProfile(): ProfileInfo {
        return ProfileInfo(
            firstName = "Анна",
            secondName = "Астафьева",
            phone = "49838532940",
            email = "snklfldva@mail.com",
            birthDay = Date(),
            gender = Gender.FEMALE,
            city = "Москва"
        )
    }

    override suspend fun updateProfile(profile: ProfileInfo) {
    }
}