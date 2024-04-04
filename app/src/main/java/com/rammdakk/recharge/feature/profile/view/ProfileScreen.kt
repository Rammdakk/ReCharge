package com.rammdakk.recharge.feature.profile.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import com.rammdakk.recharge.base.theme.IconText
import com.rammdakk.recharge.base.theme.InputIconTextField
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.profile.models.data.Gender
import com.rammdakk.recharge.feature.profile.models.presentation.ProfileScreenModel
import com.rammdakk.recharge.feature.profile.view.components.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ProfileScreen(
    firstName: String,
    secondName: String,
    phone: String,
    email: String,
    birthDay: Long,
    isMale: Boolean,
    city: String,
    onSaveClick: (ProfileScreenModel) -> Unit,
    onLogOutClick: () -> Unit
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var firstNameState by remember {
                mutableStateOf(TextFieldValue(firstName))
            }
            var secondNameState by remember {
                mutableStateOf(TextFieldValue(secondName))
            }
            var emailState by remember {
                mutableStateOf(TextFieldValue(email))
            }

            var birthDateState by remember {
                mutableLongStateOf(birthDay)
            }
            var isMaleState by remember {
                mutableStateOf(isMale)
            }
            var expanded by remember { mutableStateOf(false) }
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
                IconText(
                    modifier = modifier,
                    text = phone,
                    fontSize = 18.sp,
                    iconVector = Icons.Default.Phone
                )
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

                    var visibleCalendar by remember {
                        mutableStateOf(false)
                    }

                    if (visibleCalendar) {
                        DatePickerDialog(
                            0,
                            initialVale = birthDateState,
                            {
                                birthDateState = it
                                visibleCalendar = false
                            },
                            { visibleCalendar = false })
                    }
                    IconText(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 4.dp)
                            .clip(RoundedCornerShape(50))
                            .clickable { visibleCalendar = true },
                        text = birthDateState.convertMillisToDate(),
                        fontSize = 18.sp,
                        iconVector = Icons.Default.DateRange
                    )
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        IconText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 4.dp)
                                .clip(RoundedCornerShape(50))
                                .clickable { expanded = true },
                            text = stringResource(id = if (isMaleState) R.string.profile_gender_male else R.string.profile_gender_female),
                            fontSize = 18.sp,
                            iconVector = Icons.Default.Person
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(ReChargeTokens.BackgroundContainer.getThemedColor())
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(id = R.string.profile_gender_male)) },
                                onClick = {
                                    isMaleState = true
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(id = R.string.profile_gender_female)) },
                                onClick = {
                                    isMaleState = false
                                    expanded = false
                                }
                            )
                        }
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
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(50))
                    .background(ReChargeTokens.BackgroundColored.getThemedColor())
                    .clickable {
                        ProfileScreenModel(
                            firstName = firstNameState.text,
                            secondName = secondNameState.text,
                            phone = phone,
                            email = emailState.text,
                            birthDay = birthDateState,
                            city = cityState.text,
                            gender = if (isMaleState) Gender.MALE else Gender.FEMALE
                        ).let(onSaveClick)
//                        onSaveClick.invoke(model)
                    }
                    .padding(vertical = 10.dp),
                textAlign = TextAlign.Center,
            )
            TextPrimaryLarge(
                text = stringResource(id = R.string.exit),
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onLogOutClick.invoke()
                    }
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
private fun Long.convertMillisToDate(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    return formatter.format(Date(this))
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        "Анна",
        "Попова",
        "79811232323",
        "test@mail.ru",
        System.currentTimeMillis(),
        isMale = false,
        "Москва",
        {},
        {}
    )
}