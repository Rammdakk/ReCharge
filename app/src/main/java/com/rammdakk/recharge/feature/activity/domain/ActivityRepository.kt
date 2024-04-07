package com.rammdakk.recharge.feature.activity.domain

import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel

interface ActivityRepository {
    suspend fun getActivityInfo(activityId: Int): Result<ActivityExtendedDataModel>

    suspend fun getActivityTimeTable(activityId: Int, date: Long): Result<List<TimePadDataModel>>
}