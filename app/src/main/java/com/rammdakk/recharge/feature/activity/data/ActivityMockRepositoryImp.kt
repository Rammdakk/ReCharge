package com.rammdakk.recharge.feature.activity.data


import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import com.rammdakk.recharge.feature.activity.domain.ActivityRepository
import java.util.Date

class ActivityMockRepositoryImp : ActivityRepository {
    override suspend fun getActivityInfo(activityId: Int): Result<ActivityExtendedDataModel> =
        Result.success(
            ActivityExtendedDataModel(
                id = 1,
                imagePath = "https://riamo.ru/files/image/04/54/86/gallery!alf.jpg",
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
        date: Long
    ): Result<List<TimePadDataModel>> {
        val delta = 60000 * 60
        return Result.success(
            listOf(
                TimePadDataModel(
                    12,
                    2000.00,
                    Date(System.currentTimeMillis()),
                    Date(System.currentTimeMillis() + delta)
                ),
                TimePadDataModel(
                    13,
                    2000.00,
                    Date(System.currentTimeMillis() + delta),
                    Date(System.currentTimeMillis() + delta * 2)
                ),
                TimePadDataModel(
                    14,
                    2000.00,
                    Date(System.currentTimeMillis() + delta * 2),
                    Date(System.currentTimeMillis() + delta * 3)
                ),
                TimePadDataModel(
                    15,
                    2000.00,
                    Date(System.currentTimeMillis() + delta * 3),
                    Date(System.currentTimeMillis() + delta * 4)
                )
            )
        )
    }
}