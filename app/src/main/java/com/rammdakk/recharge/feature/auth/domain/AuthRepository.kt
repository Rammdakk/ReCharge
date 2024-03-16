package com.rammdakk.recharge.feature.auth.domain

import com.rammdakk.recharge.feature.auth.data.model.AuthPhoneResponse
import com.rammdakk.recharge.feature.auth.data.model.AuthResponse

interface AuthRepository {
    suspend fun requestCode(phone: String): Result<AuthPhoneResponse>

    suspend fun validateCode(code: String, sessionId: String, phone: String): Result<AuthResponse>
    suspend fun validateAuth(): Boolean
}