package com.rammdakk.recharge.feature.calendar.view.model


import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.rammdakk.recharge.R
import com.rammdakk.recharge.feature.calendar.data.model.Coordinates
import com.rammdakk.recharge.feature.calendar.data.model.ReservationDataModel
import java.util.Date

data class ReservationModel(
    val activityId: Int,
    val reservationId: Int,
    val imagePath: String,
    val name: String,
    val organizationName: String,
    val time: Date?,
    val address: String,
    val location: LocationInfo?,
    val status: ReservationStatus
)

data class LocationInfo(
    val longitude: Float,
    val latitude: Float
)


fun ReservationDataModel.convertToReservationModel() =
    ReservationModel(
        activityId = this.activityId,
        reservationId = this.reservationId,
        imagePath = this.imagePath,
        name = this.name,
        organizationName = this.organizationName,
        time = this.time,
        address = this.address,
        location = this.coordinates?.convertToLocationInfo(),
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
    NEW(R.string.status_new, Color(0xFFA2F4F9)),
    CONFIRMED(R.string.status_confirmed, Color(0xFFA1F3AE)),
    USED(R.string.status_used, Color(0xFFD9D9D9)),
    MISSED(R.string.status_missed, Color(0xFFD9D9D9)),
    CANCELED_BY_USER(R.string.status_canceled_user, Color(0xFFF66969)),
    CANCELED_BY_ADMIN(R.string.status_canceled_admin, Color(0xFFF66969)),
    UNKNOWN(R.string.status_unknown, Color(0xFFD9D9D9)),
}
