package com.rammdakk.recharge.catalog.data.model

data class ActivityDataModel(
    val id: Int,
    val imagePath: String,
    val name: String,
    val time: Long?,
    val duration: Long,
    val startPrice: Float,
    val organizationName: String,
    val address: String,
    val coordinates: Coordinates
)

data class Coordinates(
    val latitude: Float,
    val longitude: Float
)
