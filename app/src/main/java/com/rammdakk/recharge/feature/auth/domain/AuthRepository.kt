package com.rammdakk.recharge.feature.auth.domain

import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneResponse

interface AuthRepository {
    suspend fun requestCode(phone: String): AuthPhoneResponse

    suspend fun validateCode(code:String, sessionId: String): Boolean
}