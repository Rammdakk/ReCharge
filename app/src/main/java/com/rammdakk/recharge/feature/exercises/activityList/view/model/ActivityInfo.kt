package com.rammdakk.recharge.feature.exercises.activityList.view.model

import android.text.format.DateFormat
import android.util.Log
import com.rammdakk.recharge.feature.exercises.activityList.data.model.ActivityDataModel
import com.rammdakk.recharge.feature.exercises.activityList.data.model.Coordinates
import java.util.Date

data class ActivityInfo(
    val id: Int,
    val imagePath: String,
    val name: String,
    val organizationName: String,
    val date: Date,
    val time: String,
    val price: Float,
    val address: String,
    val location: LocationInfo,
)

data class LocationInfo(
    val longitude: Float,
    val latitude: Float
)

fun ActivityDataModel.convertToActivityInfo(): ActivityInfo? {
    return ActivityInfo(
        id = this.id,
        imagePath = this.imagePath,
        name = this.name,
        date = this.time ?: return null,
        time = convertToTimeString(this.time, this.duration),
        address = this.address,
        price = this.price,
        location = this.coordinates.convertToLocationInfo(),
        organizationName = this.organizationName
    ).also {
        Log.d("Ramil convertToActivityInfo", this.time.toString())
    }
}

private fun convertToTimeString(startDate: Date, lengthInMinutes: Long?): String {
    val startDateString = DateFormat.format("HH:mm", startDate).toString()
    if (lengthInMinutes == null) return startDateString
    val endDate = Date(startDate.time + lengthInMinutes * 60 * 1000)
    val endDateString = DateFormat.format("HH:mm", endDate).toString()
    return "$startDateString-$endDateString"
}

fun Coordinates.convertToLocationInfo(): LocationInfo {
    return LocationInfo(
        longitude = this.longitude,
        latitude = this.latitude
    )
}