package com.rammdakk.recharge.auth.domain

import com.rammdakk.recharge.auth.data.model.AuthPhoneResponse

interface AuthRepository {
    suspend fun requestCode(phone: String): AuthPhoneResponse

    suspend fun validateCode(code:String, sessionId: String): Boolean
}