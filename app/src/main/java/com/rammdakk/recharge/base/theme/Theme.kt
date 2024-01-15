package com.rammdakk.recharge.base.theme

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFA5B7E4),
    primaryContainer = Color(0xFF96AADD),
    background = Color.White,
    onPrimary = Color.Black,
    secondary = Color(0xFFD9D9D9),
    tertiary = Color(0xFFBEBEBE),
    outline = Color(0xFFEE9022)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFA5B7E4),
    background = Color(0xFFFFFBFE),
    onPrimary = Color.Black,
    secondary = Color(0xFFD9D9D9),
    tertiary = Color(0xFFBEBEBE),
    outline = Color(0xFFEE9022)
)

@Composable
fun ReChargeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val themeProvider = remember { ReChargeThemeProvider() }

    CompositionLocalProvider(
        LocalTokens provides themeProvider.getTokens(LocalContext.current)
    ) {
        content()
    }
}

class ReChargeThemeProvider : ThemeProvider {
    override fun getTokens(context: Context): Map<ReChargeTokens, Color> {
        return if (ThemeHelper.isDarkTheme(context)) {
            darkThemeTokensMap()
        } else {
            lightThemeTokensMap()
        }
    }
}


