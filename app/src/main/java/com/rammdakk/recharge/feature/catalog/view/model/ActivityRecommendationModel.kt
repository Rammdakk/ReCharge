package com.rammdakk.recharge.feature.catalog.view.model

import com.rammdakk.recharge.feature.catalog.data.model.ActivityRecommendationDataModel
import com.rammdakk.recharge.feature.catalog.data.model.Coordinates
import com.rammdakk.recharge.feature.catalog.data.model.NextActivityDataModel

data class ActivityRecommendationModel(
    val id: Int,
    val imagePath: String,
    val name: String,
    val organizationName: String,
    val startPrice: Float,
    val address: String,
)

data class NextActivityModel(
    val id: Int,
    val imagePath: String,
    val name: String,
    val organizationName: String,
    val time: Long?,
    val address: String,
    val location: LocationInfo,
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
        id = this.id,
        imagePath = this.imagePath,
        name = this.name,
        organizationName = this.organizationName,
        time = this.time,
        address = this.address,
        location = this.coordinates.convertToLocationInfo()
    )

fun Coordinates.convertToLocationInfo(): LocationInfo {
    return LocationInfo(
        longitude = this.longitude,
        latitude = this.latitude
    )
}