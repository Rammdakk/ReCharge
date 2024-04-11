package com.rammdakk.recharge.feature.activity.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class FreeSpotsDataModel(
    @JsonProperty("freeSpots") val freeSpots: Int
)