package com.rammdakk.recharge.base.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

enum class ReChargeTokens {
    Background,
    BackgroundInverse,
    BackgroundColored,
    BackgroundContainer,
    BackgroundInfo,
    BackgroundError,
    BackgroundSuccess,
    TextPrimary,
    TextPrimaryConstant,
    TextPrimaryInverse,
    TextPrimaryInverseConstant,
    TextPrimaryColored,
    TextSecondary,
    TextTertiary,
    TextError,
    TextAttention,
    ConstantWhite,
    ConstantBlack
}

val LocalTokens: ProvidableCompositionLocal<Map<ReChargeTokens, Color>> =
    staticCompositionLocalOf { lightThemeTokensMap() }

fun lightThemeTokensMap(): Map<ReChargeTokens, Color> = mapOf(
    ReChargeTokens.Background to Color.White,
    ReChargeTokens.BackgroundInverse to Color.Black,
    ReChargeTokens.BackgroundColored to Color(0xFFA5B7E4),
    ReChargeTokens.BackgroundContainer to Color(0xFF8EA5E1),
    ReChargeTokens.BackgroundInfo to Color(0xFFBEBEBE),
    ReChargeTokens.BackgroundError to Color.Red,
    ReChargeTokens.BackgroundSuccess to Color.Green,
    ReChargeTokens.TextPrimary to Color.Black,
    ReChargeTokens.TextPrimaryConstant to Color.Black,
    ReChargeTokens.TextPrimaryInverse to Color.White,
    ReChargeTokens.TextPrimaryInverseConstant to Color.White,
    ReChargeTokens.TextPrimaryColored to Color(0xFFA5B7E4),
    ReChargeTokens.TextSecondary to Color(0xC83E3E3E),
    ReChargeTokens.TextTertiary to Color(0xFFD9D9D9),
    ReChargeTokens.TextError to Color.Red,
    ReChargeTokens.TextAttention to Color(0xFFEE9022),
    ReChargeTokens.ConstantWhite to Color.White,
    ReChargeTokens.ConstantBlack to Color.Black,
)

fun darkThemeTokensMap(): Map<ReChargeTokens, Color> = mapOf(
    ReChargeTokens.Background to Color.Black,
    ReChargeTokens.BackgroundInverse to Color.White,
    ReChargeTokens.BackgroundColored to Color(0xFFA5B7E4),
    ReChargeTokens.BackgroundContainer to Color(0xFF96AADD),
    ReChargeTokens.BackgroundInfo to Color(0xFFBEBEBE),
    ReChargeTokens.BackgroundError to Color.Red,
    ReChargeTokens.BackgroundSuccess to Color.Green,
    ReChargeTokens.TextPrimary to Color.White,
    ReChargeTokens.TextPrimaryConstant to Color.Black,
    ReChargeTokens.TextPrimaryInverse to Color.Black,
    ReChargeTokens.TextPrimaryInverseConstant to Color.White,
    ReChargeTokens.TextPrimaryColored to Color(0xFFA5B7E4),
    ReChargeTokens.TextSecondary to Color(0xC83E3E3E),
    ReChargeTokens.TextError to Color.Red,
    ReChargeTokens.TextAttention to Color(0xFFEE9022),
    ReChargeTokens.ConstantWhite to Color.White,
    ReChargeTokens.ConstantBlack to Color.Black,
)

@Composable
@ReadOnlyComposable
fun ReChargeTokens.getThemedColor(): Color = LocalTokens.current.getValue(this)