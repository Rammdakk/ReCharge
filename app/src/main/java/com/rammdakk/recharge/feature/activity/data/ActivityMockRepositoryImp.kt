package com.rammdakk.recharge.feature.activity.data


import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import com.rammdakk.recharge.feature.activity.data.model.UserBookingDataModel
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import java.util.Date

class ActivityMockRepositoryImp : ActivityRepository {
    override suspend fun getActivityInfo(activityId: Int): Result<ActivityExtendedDataModel> =
        Result.success(
            ActivityExtendedDataModel(
                id = 1,
                imagePath = "https://lrhotel.ru/upload/resize_cache/iblock/688/767_500_2/hys84kpkx508fkmxh77gm3s1ve3afbky.JPG",
                name = "Pool",
                adminPhoneWA = "+7852367821943",
                adminTgUsername = "rammdakk",
                locationName = "Чайка бассейн",
                locationAddress = "Москва, покровскицй бульвар 11",
                activityDescription = "Очень крутоц бассей",
                cancellationMessage = "Отмена не менее чем за 12 часов",
                warning = "Нужна справка"
            )
        )


    override suspend fun getActivityTimeTable(
        activityId: Int,
        date: Date
    ): Result<List<TimePadDataModel>> {
        val delta = 60000 * 60
        return Result.success(
            listOf(
                TimePadDataModel(
                    12,
                    2000.00,
                    Date(System.currentTimeMillis()),
                    delta
                ),
                TimePadDataModel(
                    13,
                    2000.00,
                    Date(System.currentTimeMillis() + delta),
                    delta * 2
                ),
                TimePadDataModel(
                    14,
                    2000.00,
                    Date(System.currentTimeMillis() + delta * 2),
                    delta * 3
                ),
                TimePadDataModel(
                    15,
                    2000.00,
                    Date(System.currentTimeMillis() + delta * 3),
                    delta * 4
                )
            )
        )
    }

    override suspend fun getUsersMaxNumber(tabId: Int): Result<Int> {
        return Result.success(5)
    }

    override suspend fun reserveActivity(tabId: Int, userBookingInfo: UserBookingDataModel) =
        Result.success(Unit)
}