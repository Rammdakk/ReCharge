package com.rammdakk.recharge.feature.catalog.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CategoryDataModel(
    @JsonProperty("image") val imagePath: String,
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String
)