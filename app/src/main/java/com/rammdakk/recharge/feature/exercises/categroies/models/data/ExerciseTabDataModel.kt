package com.rammdakk.recharge.feature.exercises.categroies.models.data

import com.fasterxml.jackson.annotation.JsonProperty

data class ExerciseTabDataModel(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("id")
    val id: Int
)
