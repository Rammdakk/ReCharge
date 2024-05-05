package com.rammdakk.recharge.feature.activity.domain

import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import java.util.Date

class ActivityUseCaseImpl(
    private val activityRepository: ActivityRepository
) : ActivityUseCase {

    override suspend fun getActivityInfo(activityId: Int): Result<ActivityExtendedDataModel> =
        activityRepository.getActivityInfo(activityId)

    override suspend fun getActivityTimeTable(
        activityId: Int,
        date: Date
    ): Result<List<TimePadDataModel>> =
        activityRepository.getActivityTimeTable(activityId, date)
}