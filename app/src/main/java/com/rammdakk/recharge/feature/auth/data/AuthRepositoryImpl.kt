package com.rammdakk.recharge.feature.auth.data

import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneResponse
import com.rammdakk.recharge.feature.auth.data.model.ConditionalInfo
import com.rammdakk.recharge.feature.auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {
    override suspend fun requestCode(phone: String): AuthPhoneResponse {
        delay(200)
        return AuthPhoneResponse(
            true, null, "423678",
            "Введите \n" +
                    "код из смс",
            5,
            ConditionalInfo(
                "Совершая авторизацию, вы соглашаетесь \n" +
                        "с правилами работы сервиса", "http://www.google.com"
            ),
        )
    }

    override suspend fun validateCode(code: String, sessionId: String): Boolean {
        withContext(Dispatchers.IO) {

        }
        return true
    }
}