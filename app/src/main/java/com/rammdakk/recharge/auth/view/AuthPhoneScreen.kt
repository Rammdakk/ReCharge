package com.rammdakk.recharge.auth.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rammdakk.recharge.R

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun AuthPreview() {
    AuthPhoneScreen(
        greetingText = "Привет!",
        onClick = { },
        hintText = "Номер телефона",
        extraInfo = mutableStateOf(ExtraInfo("Отправить код повторно", false, null)),
    )
}

@Composable
fun AuthPhoneScreen(
    greetingText: String,
    hintText: String? = null,
    onClick: (String) -> Unit,
    extraInfo: State<ExtraInfo?>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFA5B7E4),
                        Color(0x70A5B7E4)
                    )
                )
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxHeight(0.3f))
        Text(
            modifier = Modifier.padding(bottom = 30.dp),
            text = greetingText,
            textAlign = TextAlign.Center,
            fontSize = 45.sp,
            lineHeight = 50.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        PhoneCell(hint = hintText) { str -> onClick.invoke(str) }
        extraInfo.value?.let { info ->
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp),
                text = info.message ?: "",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = if (info.isError) Color.Red else Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhoneCell(hint: String? = null, onVerifyClick: (String) -> Unit) {
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
                .background(Color.White, shape = RoundedCornerShape(50))
                .onSizeChanged {
                    height = it.height
                }
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .align(Alignment.CenterStart),
            value = text,
            onValueChange = {
                isButtonAvailable = it.text.length == 10
                text = it
            },
            textStyle = TextStyle.Default.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onVerifyClick(text.text)
            }),
        )
        { innerTextField ->
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "+7 ", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Box {
                    if (text.text.isEmpty()) {
                        hint?.let { Text(it, fontSize = 24.sp, color = Color.LightGray) }
                    }
                    innerTextField()
                }
            }
        }
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
                Color.White
            )
        }
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }
