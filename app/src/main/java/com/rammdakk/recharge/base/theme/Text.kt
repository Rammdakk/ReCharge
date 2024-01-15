package com.rammdakk.recharge.base.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HeaderTextPrimary(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 24.sp,
        lineHeight = 26.sp,
        color = ReChargeTokens.TextPrimary.getThemedColor(),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun HeaderTextPrimaryInverse(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        color = ReChargeTokens.TextPrimaryInverse.getThemedColor(),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TextPrimaryLarge(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        color = ReChargeTokens.TextPrimary.getThemedColor(),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TextPrimaryLargeInverse(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 20.sp,
        lineHeight = 22.sp,
        color = ReChargeTokens.TextPrimaryInverse.getThemedColor(),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TextPrimaryMedium(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        color = ReChargeTokens.TextPrimary.getThemedColor(),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TextPrimaryMediumInverse(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        color = ReChargeTokens.TextPrimaryInverse.getThemedColor(),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TextPrimarySmall(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        color = ReChargeTokens.TextPrimary.getThemedColor(),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TextPrimarySmallInverse(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        color = ReChargeTokens.TextPrimaryInverse.getThemedColor(),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun PlainText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        color = ReChargeTokens.TextPrimary.getThemedColor(),
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun PlainTextInverse(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        color = ReChargeTokens.TextPrimaryInverse.getThemedColor(),
        fontWeight = FontWeight.Medium
    )
}

