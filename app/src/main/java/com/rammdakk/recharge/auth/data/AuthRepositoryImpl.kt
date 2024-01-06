package com.rammdakk.recharge.auth.data

import com.rammdakk.recharge.auth.data.model.AuthPhoneResponse
import com.rammdakk.recharge.auth.data.model.ConditionalInfo
import com.rammdakk.recharge.auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {
    override suspend fun requestCode(phone: String): AuthPhoneResponse {
        delay(2000)
        return AuthPhoneResponse(
            true, "", "Введите \n" +
                    "код из смс", 5, ConditionalInfo(
                "Совершая авторизацию, вы соглашаетесь \n" +
                        "с правилами работы сервиса", ""
            ), "Отправить код повторно"
        )
    }

    override suspend fun validateCode(code: String, sessionId: String): Boolean {
        withContext(Dispatchers.IO) {

        }
        return true
    }
}