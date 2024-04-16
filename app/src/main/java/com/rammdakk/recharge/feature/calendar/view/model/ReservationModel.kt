package com.rammdakk.recharge.feature.calendar.view.model


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
        location = this.coordinates?.convertToLocationInfo()
    )

fun Coordinates.convertToLocationInfo(): LocationInfo {
    return LocationInfo(
        longitude = this.longitude,
        latitude = this.latitude
    )
}