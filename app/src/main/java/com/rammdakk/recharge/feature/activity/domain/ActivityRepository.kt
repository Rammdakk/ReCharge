package com.rammdakk.recharge.feature.activity.domain

import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import java.util.Date

interface ActivityRepository {
    suspend fun getActivityInfo(id: Int): ActivityExtendedDataModel

    suspend fun getActivityTimeTable(date: Date): List<TimePadDataModel>
}