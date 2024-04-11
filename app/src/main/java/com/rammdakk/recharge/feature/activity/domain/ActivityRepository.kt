package com.rammdakk.recharge.feature.activity.domain

import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import com.rammdakk.recharge.feature.activity.data.model.UserBookingDataModel
import java.util.Date

interface ActivityRepository {
    suspend fun getActivityInfo(activityId: Int): Result<ActivityExtendedDataModel>

    suspend fun getActivityTimeTable(activityId: Int, date: Date): Result<List<TimePadDataModel>>

    suspend fun getUsersMaxNumber(tabId: Int): Result<Int>

    suspend fun reserveActivity(tabId: Int, userBookingInfo: UserBookingDataModel): Result<Unit>
}