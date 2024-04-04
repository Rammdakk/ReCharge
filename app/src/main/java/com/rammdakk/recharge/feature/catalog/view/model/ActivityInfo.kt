package com.rammdakk.recharge.feature.catalog.view.model

import android.text.format.DateFormat
import com.rammdakk.recharge.feature.catalog.data.model.ActivityDataModel
import com.rammdakk.recharge.feature.catalog.data.model.Coordinates

data class ActivityInfo(
    val id: Int,
    val imagePath: String,
    val name: String,
    val organizationName: String,
    val time: String?,
    val startPrice: Float,
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
        time = this.time?.let { DateFormat.format("d MMM, hh:mm", it).toString() },
        address = this.address,
        startPrice = this.startPrice,
        location = this.coordinates.convertToLocationInfo(),
        organizationName = this.organizationName
    )

fun Coordinates.convertToLocationInfo(): LocationInfo {
    return LocationInfo(
        longitude = this.longitude,
        latitude = this.latitude
    )
}