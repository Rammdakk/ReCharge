package com.rammdakk.recharge.feature.exercises.models.data

import com.fasterxml.jackson.annotation.JsonProperty

data class SportTypeDataModel(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("imageUrl")
    val imageUrl: String
)
