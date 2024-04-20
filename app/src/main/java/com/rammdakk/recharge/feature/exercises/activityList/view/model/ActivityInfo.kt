package com.rammdakk.recharge.feature.exercises.activityList.view.model

import android.text.format.DateFormat
import com.rammdakk.recharge.feature.exercises.activityList.data.model.ActivityDataModel
import com.rammdakk.recharge.feature.exercises.activityList.data.model.Coordinates

data class ActivityInfo(
    val id: Int,
    val imagePath: String,
    val name: String,
    val organizationName: String,
    val time: String?,
    val price: Float,
    val address: String,
    val location: LocationInfo,
)

data class LocationInfo(
    val longitude: Float,
    val latitude: Float
)

fun ActivityDataModel.convertToActivityInfo() =
    ActivityInfo(
        id = this.id,
        imagePath = this.imagePath,
        name = this.name,
        time = this.time?.let { DateFormat.format("d MMM", it).toString() },
        address = this.address,
        price = this.price,
        location = this.coordinates.convertToLocationInfo(),
        organizationName = this.organizationName
    )

fun Coordinates.convertToLocationInfo(): LocationInfo {
    return LocationInfo(
        longitude = this.longitude,
        latitude = this.latitude
    )
}