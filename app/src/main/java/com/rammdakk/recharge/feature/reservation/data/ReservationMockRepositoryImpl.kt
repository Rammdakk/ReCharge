package com.rammdakk.recharge.feature.reservation.data

import com.rammdakk.recharge.feature.reservation.data.model.ReservationDataModel
import com.rammdakk.recharge.feature.reservation.domain.ReservationRepository

class ReservationMockRepositoryImpl : ReservationRepository {
//    override suspend fun getActivityInfo(id: Int): Result<ActivityExtendedDataModel> {
//        return Result.success(
//            ActivityExtendedDataModel(
//                id = 1,
//                imagePath = "https://lrhotel.ru/upload/resize_cache/iblock/688/767_500_2/hys84kpkx508fkmxh77gm3s1ve3afbky.JPG",
//                name = "Pool",
//                adminPhoneWA = "+7852367821943",
//                adminTgUsername = "rammdakk",
//                locationName = "Чайка бассейн",
//                locationAddress = "Москва, покровскицй бульвар 11",
//                activityDescription = "Очень крутоц бассей",
//                cancellationMessage = "Отмена не менее чем за 12 часов",
//                warning = "Нужна справка"
//            )
//        )
//    }

    override suspend fun getReservationInfo(reservationId: Int): Result<ReservationDataModel> {
        return Result.success(
            ReservationDataModel(
                reservationId = 1367,
                activityId = 2,
                time = System.currentTimeMillis(),
                accessCode = "gszdhkaetquyef1267euqiwehcbadk"
            )
        )
    }
}