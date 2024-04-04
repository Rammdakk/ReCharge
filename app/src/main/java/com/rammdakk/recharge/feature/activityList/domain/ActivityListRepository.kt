package com.rammdakk.recharge.feature.activityList.domain

import com.rammdakk.recharge.feature.activityList.data.model.ActivityListDataModel

interface ActivityListRepository {
    suspend fun getActivities(activityCatId: Int, date: Long): Result<ActivityListDataModel>

}