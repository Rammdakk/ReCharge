package com.rammdakk.recharge.base.data.sp

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.qualifiers.ApplicationContext


class CustomSharedPreferences(
    @ApplicationContext context: Context
) {
    val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun <T> saveData(element: T, key: String): Boolean = runCatching {
        val value = ObjectMapper().writeValueAsString(element)
        sharedPreferences.edit().putString(key, value).apply()
        true
    }.getOrElse { false }

    fun <T> getData(key: String, valueType: Class<T>): T? = runCatching {
        val value = sharedPreferences.getString(key, null)
        ObjectMapper().readValue(value, valueType)
    }.getOrNull()

}