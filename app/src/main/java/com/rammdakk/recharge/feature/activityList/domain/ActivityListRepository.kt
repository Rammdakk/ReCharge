package com.rammdakk.recharge.feature.activityList.domain

import com.rammdakk.recharge.feature.activityList.data.model.ActivityListDataModel
import java.util.Date

interface ActivityListRepository {
    suspend fun getActivities(activityCatId: Int, date: Date): Result<ActivityListDataModel>

}