package com.rammdakk.recharge.feature.activity.domain

import com.rammdakk.recharge.feature.activity.data.model.UserBookingDataModel

interface ReservationInfoUseCase {
    suspend fun getUsersMaxNumber(tabId: Int): Result<Int>

    suspend fun reserveActivity(tabId: Int, userBookingInfo: UserBookingDataModel): Result<Unit>
}