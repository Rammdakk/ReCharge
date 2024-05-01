package com.rammdakk.recharge.feature.calendar.data

import com.rammdakk.recharge.feature.calendar.data.model.Coordinates
import com.rammdakk.recharge.feature.calendar.data.model.ReservationDataModel
import com.rammdakk.recharge.feature.calendar.domain.CalendarRepository
import java.util.Date

class CalendarMockRepositoryImpl(
) : CalendarRepository {

    override suspend fun loadReservations(
        startDate: Date,
        endDate: Date
    ): Result<List<ReservationDataModel>> =
        Result.success(
            listOf(
                ReservationDataModel(
                    activityId = 3567,
                    reservationId = 6253,
                    imagePath = "https://lrhotel.ru/upload/resize_cache/iblock/688/767_500_2/hys84kpkx508fkmxh77gm3s1ve3afbky.JPG",
                    name = "2-ух часовое занятие с тренером очень интереснг длиныый текст 2-ух часовое занятие с тренером очень интереснг длиныый текст",
                    time = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 10),
                    address = "м.Курская, Земляной вал, 33",
                    organizationName = "Бассейн Чайка",
                    coordinates = Coordinates(55.757114f, 37.65905f),
                    reservationStatus = 1
                ),
                ReservationDataModel(
                    activityId = 27,
                    reservationId = 1345,
                    imagePath = "https://lrhotel.ru/upload/resize_cache/iblock/688/767_500_2/hys84kpkx508fkmxh77gm3s1ve3afbky.JPG",
                    name = "2-ух часовое занятие с тренером очень интереснг длиныый текст 2-ух часовое занятие с тренером очень интереснг длиныый текст",
                    time = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 10),
                    address = "м.Курская, Земляной вал, 33",
                    organizationName = "Бассейн Чайка",
                    coordinates = Coordinates(55.757114f, 37.65905f),
                    reservationStatus = 4
                )
            )
        )

}