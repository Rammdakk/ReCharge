package com.rammdakk.recharge.base.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
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
fun TextPrimaryLargeThemed(
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
        color = ReChargeTokens.TextPrimaryColored.getThemedColor(),
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
fun TextSecondaryLarge(
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
        color = ReChargeTokens.TextSecondary.getThemedColor(),
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun TextSecondaryMedium(
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
        color = ReChargeTokens.TextSecondary.getThemedColor(),
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun TextSecondarySmall(
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
        color = ReChargeTokens.TextSecondary.getThemedColor(),
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun PlainTextLarge(
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
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun PlainTextLargeInverse(
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
        fontWeight = FontWeight.Normal
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
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun PlainSmallText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        color = ReChargeTokens.TextPrimary.getThemedColor(),
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun PlainTextSmallInverse(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        color = ReChargeTokens.TextPrimaryInverse.getThemedColor(),
        fontWeight = FontWeight.Normal
    )
}


@Composable
fun PlainTextBold(
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
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun PlainTextBoldInverse(
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
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TextError(
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
        color = ReChargeTokens.TextError.getThemedColor(),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int? = null,
    iconVector: ImageVector? = null,
    iconColor: Color = ReChargeTokens.TextPrimary.getThemedColor(),
    text: String,
    hint: String? = null,
    fontSize: TextUnit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        var height by remember {
            mutableIntStateOf(0)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .onSizeChanged {
                    if (height == 0) {
                        height = it.height
                    }
                }
                .background(
                    ReChargeTokens.TextPrimaryInverse.getThemedColor(),
                    shape = RoundedCornerShape(50)
                )
        ) {
            iconId?.let {
                Icon(
                    painterResource(id = it),
                    "",
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFA5B7E4))
                        .height(height = (height).pxToDp() - 4.dp)
                        .padding(3.dp),
                    iconColor
                )
            } ?: iconVector?.let {
                Icon(
                    imageVector = it,
                    "",
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFA5B7E4))
                        .height(height = (height).pxToDp() - 4.dp)
                        .padding(3.dp),
                    iconColor
                )
            }
            Box(
                modifier = Modifier
                    .padding(all = 6.dp)
                    .wrapContentHeight()
            ) {
                if (text.isEmpty()) {
                    hint?.let {
                        Text(
                            it,
                            fontSize = fontSize,
                            color = ReChargeTokens.TextTertiary.getThemedColor()
                        )
                    }
                }
                Text(
                    text = text,
                    style = TextStyle.Default.copy(
                        fontSize = fontSize,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
fun InputIconTextField(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int? = null,
    iconVector: ImageVector? = null,
    iconColor: Color = ReChargeTokens.TextPrimary.getThemedColor(),
    value: TextFieldValue,
    hint: String? = null,
    fontSize: TextUnit,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (TextFieldValue) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        var height by remember {
            mutableIntStateOf(0)
        }
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    ReChargeTokens.TextPrimaryInverse.getThemedColor(),
                    shape = RoundedCornerShape(50)
                )
                .onSizeChanged {
                    if (height == 0) {
                        height = it.height
                    }
                }
                .align(Alignment.CenterStart),
            value = value,
            readOnly = readOnly,
            onValueChange = onValueChange,
            textStyle = TextStyle.Default.copy(fontSize = fontSize, fontWeight = FontWeight.Normal),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
        )
        { innerTextField ->
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                iconId?.let {
                    Icon(
                        painterResource(id = it),
                        "",
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFFA5B7E4))
                            .height(height = (height).pxToDp() - 4.dp)
                            .padding(3.dp)
                            .aspectRatio(1f, true),
                        iconColor
                    )
                } ?: iconVector?.let {
                    Icon(
                        imageVector = it,
                        "",
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFFA5B7E4))
                            .height(height = (height).pxToDp() - 4.dp)
                            .padding(3.dp)
                            .aspectRatio(1f, true),
                        iconColor
                    )
                }
                Box(modifier = Modifier.padding(all = 6.dp)) {
                    if (value.text.isEmpty()) {
                        hint?.let {
                            Text(
                                it,
                                fontSize = fontSize,
                                color = ReChargeTokens.TextTertiary.getThemedColor()
                            )
                        }
                    }
                    innerTextField()
                }
            }
        }
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }