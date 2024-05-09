package com.rammdakk.recharge.feature.auth.view

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.PlainTextBoldInverse
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextError
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.theme.pxToDp

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun AuthCodePreview() {
    AuthCodeValidationScreen(
        greetingText = "Введите \n" +
                "код из смс",
        codeSize = 5,
        onBackPressed = {},
        onClick = { },
        errorMessage = mutableStateOf(null),
        onRequestCodeClick = {},
        bottomInfo = mutableStateOf(
            BottomInfo(
                "Совершая авторизацию, вы соглашаетесь \n" +
                        "с правилами работы сервиса", {}
            )
        )
    )
}

@Composable
fun AuthCodeValidationScreen(
    greetingText: String,
    codeSize: Int,
    onBackPressed: () -> Unit,
    onClick: (String) -> Unit,
    errorMessage: State<String?>,
    onRequestCodeClick: () -> Unit,
    bottomInfo: State<BottomInfo?>
) {
    BackHandler {
        onBackPressed.invoke()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ReChargeTokens.BackgroundColored.getThemedColor()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(id = R.drawable.navigate_back),
            "",
            modifier = Modifier
                .padding(top = 10.dp, start = 2.dp)
                .align(Alignment.Start)
                .clip(RoundedCornerShape(50))
                .clickable { onBackPressed.invoke() }
                .height(height = 45.dp)
                .aspectRatio(1f, true)
                .padding(10.dp), ReChargeTokens.TextPrimaryInverse.getThemedColor())
        Box(modifier = Modifier.fillMaxHeight(0.25f))
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .padding(bottom = 30.dp)
                .padding(horizontal = 10.dp),
            text = greetingText,
            textAlign = TextAlign.Center,
            fontSize = 45.sp,
            lineHeight = 50.sp,
            color = ReChargeTokens.TextPrimaryInverse.getThemedColor(),
            fontWeight = FontWeight.Bold
        )
        CodeCell(codeSize) { str -> onClick.invoke(str) }
        TextError(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            text = errorMessage.value ?: "",
        )
        PlainTextBoldInverse(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp)
                .clickable { onRequestCodeClick.invoke() },
            text = stringResource(id = R.string.resend_code),
            textAlign = TextAlign.Center,
        )

        bottomInfo.value?.let { info ->
            Box(modifier = Modifier.fillMaxSize()) {
                PlainTextBoldInverse(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(vertical = 20.dp)
                        .clickable {
                            info.onClick?.invoke()
                        },
                    text = info.message,
                )
            }
        }
    }
}

@Composable
fun CodeCell(codeSize: Int, onVerifyClick: (String) -> Unit) {
    var text by remember {
        mutableStateOf(TextFieldValue())
    }
    var isButtonAvailable by remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 30.dp)
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
                    height = it.height
                }
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .align(Alignment.Center),
            value = text,
            onValueChange = {
                isButtonAvailable = it.text.length == codeSize
                text = it
            },
            textStyle = TextStyle.Default.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = ReChargeTokens.TextPrimary.getThemedColor()
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onVerifyClick(text.text)
            }),
        )
        if (isButtonAvailable) {
            Icon(
                painterResource(id = R.drawable.navigate_next),
                "",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFA5B7E4))
                    .clickable {
                        keyboardController?.hide()
                        onVerifyClick(text.text)
                    }
                    .height(height = (height).pxToDp() - 10.dp)
                    .padding(10.dp)
                    .aspectRatio(1f, true),
                ReChargeTokens.TextPrimaryInverse.getThemedColor()
            )
        }
    }
}

