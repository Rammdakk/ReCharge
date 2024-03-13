package com.rammdakk.recharge.feature.catalog.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryDataModel(
    @JsonProperty("image") val imagePath: String,
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String
)