package com.rammdakk.recharge.feature.exercises.activityList.view.model

import androidx.annotation.StringRes
import com.rammdakk.recharge.R

enum class SortingTypes(@StringRes val res: Int) {
    TIME(R.string.exercise_sort_date),
    PRICE_LOW(R.string.exercise_sort_price_low),
    PRICE_HIGH(R.string.exercise_sort_price_high),
    LOCATION(R.string.exercise_sort_location)
}