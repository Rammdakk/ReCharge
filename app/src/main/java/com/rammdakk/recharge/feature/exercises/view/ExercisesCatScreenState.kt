package com.rammdakk.recharge.feature.exercises.view

import androidx.compose.runtime.State
import com.rammdakk.recharge.feature.exercises.models.presentation.ExercisesTab
import com.rammdakk.recharge.feature.exercises.models.presentation.SportTypeItem

sealed interface ExercisesCatScreenState {
    data object Idle : ExercisesCatScreenState

    data class Loaded(
        val selectedId: State<Int>,
        val tabs: State<List<ExercisesTab>>,
        val items: State<List<SportTypeItem>>
    ) : ExercisesCatScreenState
}

