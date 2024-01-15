package com.rammdakk.recharge.catalog.view.model

import android.text.format.DateFormat
import com.rammdakk.recharge.catalog.data.model.ActivityDataModel
import com.rammdakk.recharge.catalog.data.model.Coordinates

data class ActivityInfo(
    val id: Int,
    val imagePath: String,
    val name: String,
    val time: String,
    val price: String,
    val address: String,
    val location: LocationInfo,
)

data class LocationInfo(
    val longitude: Float,
    val latitude: Float
)

fun ActivityDataModel.convertToActivityInfo(): ActivityInfo {
    return ActivityInfo(
        id = this.id,
        imagePath = this.imagePath,
        name = this.name,
        time = DateFormat.format("d MMM", this.time).toString(),
        address = this.address,
        price = "${this.price}₽",
        location = this.coordinates.convertToLacationInfo(),
    )
}

fun Coordinates.convertToLacationInfo(): LocationInfo {
    return LocationInfo(
        longitude = this.longitude,
        latitude = this.latitude
    )
}