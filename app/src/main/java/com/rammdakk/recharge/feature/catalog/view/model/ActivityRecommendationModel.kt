package com.rammdakk.recharge.feature.catalog.view.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.rammdakk.recharge.R
import com.rammdakk.recharge.feature.catalog.data.model.ActivityRecommendationDataModel
import com.rammdakk.recharge.feature.catalog.data.model.Coordinates
import com.rammdakk.recharge.feature.catalog.data.model.NextActivityDataModel
import java.util.Date

data class ActivityRecommendationModel(
    val id: Int,
    val imagePath: String,
    val name: String,
    val organizationName: String,
    val startPrice: Float,
    val address: String,
)

data class NextActivityModel(
    val activityId: Int,
    val reservationId: Int,
    val imagePath: String,
    val name: String,
    val organizationName: String,
    val time: Date?,
    val address: String,
    val location: LocationInfo,
    val status: ReservationStatus
)

data class LocationInfo(
    val longitude: Float,
    val latitude: Float
)

fun ActivityRecommendationDataModel.convertToActivityInfo() =
    ActivityRecommendationModel(
        id = this.id,
        imagePath = this.imagePath,
        name = this.name,
        address = this.address,
        startPrice = this.startPrice,
        organizationName = this.organizationName
    )

fun NextActivityDataModel.convertToActivityInfo() =
    NextActivityModel(
        activityId = this.activityId,
        reservationId = this.reservationId,
        imagePath = this.imagePath,
        name = this.name,
        organizationName = this.organizationName,
        time = this.time,
        address = this.address,
        location = this.coordinates.convertToLocationInfo(),
        status = runCatching { ReservationStatus.entries[reservationStatus] }.getOrDefault(
            ReservationStatus.UNKNOWN
        )
    )

fun Coordinates.convertToLocationInfo(): LocationInfo {
    return LocationInfo(
        longitude = this.longitude,
        latitude = this.latitude
    )
}

enum class ReservationStatus(
    @StringRes val stringRes: Int,
    val cellColor: Color
) {
    NEW(R.string.status_new, Color(0xFF4974f5)),
    CONFIRMED(R.string.status_confirmed, Color(0xFFA1F3AE)),
    USED(R.string.status_used, Color(0xFFD9D9D9)),
    MISSED(R.string.status_missed, Color(0xFFD9D9D9)),
    CANCELED_BY_USER(R.string.status_canceled_user, Color(0xFFF66969)),
    CANCELED_BY_ADMIN(R.string.status_canceled_admin, Color(0xFFF66969)),
    UNKNOWN(R.string.status_unknown, Color(0xFFD9D9D9)),
}
