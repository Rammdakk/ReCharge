package com.rammdakk.recharge.feature.activity.domain

import com.rammdakk.recharge.feature.activity.data.model.UserBookingDataModel

class ReservationInfoUseCaseImpl(private val activityRepository: ActivityRepository) :
    ReservationInfoUseCase {
    override suspend fun getUsersMaxNumber(tabId: Int): Result<Int> =
        activityRepository.getUsersMaxNumber(tabId)

    override suspend fun reserveActivity(
        tabId: Int,
        userBookingInfo: UserBookingDataModel
    ): Result<Unit> = activityRepository.reserveActivity(tabId, userBookingInfo)
}