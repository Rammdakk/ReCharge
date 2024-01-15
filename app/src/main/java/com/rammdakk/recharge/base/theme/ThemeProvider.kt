package com.rammdakk.recharge.base.theme

import android.content.Context
import androidx.compose.ui.graphics.Color

interface ThemeProvider {
    fun getTokens(context: Context): Map<ReChargeTokens, Color>
}