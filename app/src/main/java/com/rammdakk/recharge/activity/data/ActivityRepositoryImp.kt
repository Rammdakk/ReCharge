package com.rammdakk.recharge.activity.data

import com.rammdakk.recharge.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.activity.data.model.TimePadDataModel
import com.rammdakk.recharge.activity.domain.ActivityRepository
import kotlinx.coroutines.Dispatchers
import java.util.Date

class ActivityRepositoryImp(
    private val dispatchers: Dispatchers,
) : ActivityRepository {
    override suspend fun getActivityInfo(id: Int): ActivityExtendedDataModel {
        return ActivityExtendedDataModel(
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
    }

    override suspend fun getActivityTimeTable(date: Date): List<TimePadDataModel> {
        val delta = 60000 * 60
        return listOf(
            TimePadDataModel(
                12,
                2000.00,
                Date(System.currentTimeMillis()),
                Date(System.currentTimeMillis() + delta)
            ),
            TimePadDataModel(
                12,
                2000.00,
                Date(System.currentTimeMillis() + delta),
                Date(System.currentTimeMillis() + delta * 2)
            ),
            TimePadDataModel(
                12,
                2000.00,
                Date(System.currentTimeMillis() + delta * 2),
                Date(System.currentTimeMillis() + delta * 3)
            ),
            TimePadDataModel(
                12,
                2000.00,
                Date(System.currentTimeMillis() + delta * 3),
                Date(System.currentTimeMillis() + delta * 4)
            )
        )
    }
}