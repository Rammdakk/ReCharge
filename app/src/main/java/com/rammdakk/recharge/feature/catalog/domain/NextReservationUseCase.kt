package com.rammdakk.recharge.feature.catalog.domain

import com.rammdakk.recharge.feature.catalog.data.model.NextActivityDataModel

interface NextReservationUseCase {

    suspend fun getNextActivityInfo(): Result<NextActivityDataModel>
}