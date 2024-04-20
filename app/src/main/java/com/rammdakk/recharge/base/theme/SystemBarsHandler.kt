package com.rammdakk.recharge.base.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun setSystemBarsColors(statusBarColor: Color, navBarColor: Color) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(navBarColor)
    systemUiController.setStatusBarColor(statusBarColor)
    systemUiController.isNavigationBarContrastEnforced = false
}
