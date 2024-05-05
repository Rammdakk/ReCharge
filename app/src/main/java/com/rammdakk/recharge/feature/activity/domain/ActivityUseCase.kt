package com.rammdakk.recharge.feature.activity.domain

import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import java.util.Date

interface ActivityUseCase {

    suspend fun getActivityInfo(activityId: Int): Result<ActivityExtendedDataModel>

    suspend fun getActivityTimeTable(activityId: Int, date: Date): Result<List<TimePadDataModel>>
}