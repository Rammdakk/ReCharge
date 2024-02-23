package com.rammdakk.recharge.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.HeaderTextPrimary
import com.rammdakk.recharge.base.theme.InputIconTextField
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.getThemedColor
import java.util.Date

@Composable
fun ProfileScreen(
    firstName: String,
    secondName: String,
    phone: String,
    email: String,
    birthDay: Date,
    isMale: Boolean,
    city: String
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HeaderTextPrimary(
                    text = stringResource(id = R.string.profile_header),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var firstNameState by remember {
                mutableStateOf(TextFieldValue(firstName))
            }
            var secondNameState by remember {
                mutableStateOf(TextFieldValue(secondName))
            }
            var phoneState by remember {
                mutableStateOf(TextFieldValue(phone))
            }
            var emailState by remember {
                mutableStateOf(TextFieldValue(email))
            }

            TODO("Поправить работу с датой")
            var birthDateState by remember {
                mutableStateOf(birthDay)
            }
            var isMaleState by remember {
                mutableStateOf(isMale)
            }
            var cityState by remember {
                mutableStateOf(TextFieldValue(city))
            }

            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .wrapContentSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(ReChargeTokens.BackgroundColored.getThemedColor())
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 8.dp)

                InputIconTextField(
                    modifier = modifier,
                    value = firstNameState,
                    fontSize = 18.sp,
                ) { fieldValue ->
                    firstNameState = fieldValue
                }
                InputIconTextField(
                    modifier = modifier,
                    value = secondNameState,
                    fontSize = 18.sp,
                ) { fieldValue ->
                    secondNameState = fieldValue
                }
                InputIconTextField(
                    modifier = modifier,
                    value = phoneState,
                    fontSize = 18.sp,
                    iconVector = Icons.Default.Phone
                ) { fieldValue ->
                    phoneState = fieldValue
                }
                InputIconTextField(
                    modifier = modifier,
                    value = emailState,
                    fontSize = 18.sp,
                    iconVector = Icons.Default.Email
                ) { fieldValue ->
                    emailState = fieldValue
                }
                Row(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InputIconTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 4.dp),
                        value = cityState,
                        fontSize = 18.sp,
                        iconVector = Icons.Default.DateRange
                    ) { fieldValue ->
                        cityState = fieldValue
                    }
                    InputIconTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp),
                        value = cityState,
                        fontSize = 18.sp,
                        iconVector = Icons.Default.Person
                    ) { fieldValue ->
                        cityState = fieldValue
                    }
                }
                InputIconTextField(
                    modifier = modifier,
                    value = cityState,
                    fontSize = 18.sp,
                    iconVector = Icons.Default.LocationOn
                ) { fieldValue ->
                    cityState = fieldValue
                }
            }

            TextPrimaryLarge(
                text = stringResource(id = R.string.save),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(50))
                    .background(ReChargeTokens.BackgroundColored.getThemedColor())
                    .clickable { }
                    .padding(vertical = 10.dp),
                textAlign = TextAlign.Center,
            )
            TextPrimaryLarge(
                text = stringResource(id = R.string.exit),
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { }
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun preview() {
    ProfileScreen(
        "Анна",
        "Попова",
        "79811232323",
        "test@mail.ru",
        Date(),
        isMale = false,
        "Москва"
    )
}