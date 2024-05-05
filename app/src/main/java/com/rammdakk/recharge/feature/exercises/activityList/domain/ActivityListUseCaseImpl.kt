package com.rammdakk.recharge.feature.exercises.activityList.domain

import com.rammdakk.recharge.feature.exercises.activityList.data.model.ActivityListDataModel
import java.util.Date

class ActivityListUseCaseImpl(
    private val activityListRepository: ActivityListRepository
) : ActivityListUseCase {
    override suspend fun getActivities(
        activityCatId: Int,
        date: Date
    ): Result<ActivityListDataModel> {
        return activityListRepository.getActivities(activityCatId, date)
    }
}