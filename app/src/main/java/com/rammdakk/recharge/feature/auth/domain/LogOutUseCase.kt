package com.rammdakk.recharge.feature.auth.domain

interface LogOutUseCase {

    /**
     * Удаление данных пользователя
     */
    suspend fun logOut(): Boolean
}