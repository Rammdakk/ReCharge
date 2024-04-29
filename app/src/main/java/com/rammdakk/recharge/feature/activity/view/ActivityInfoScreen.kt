package com.rammdakk.recharge.feature.activity.view

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.HeaderTextPrimary
import com.rammdakk.recharge.base.theme.HeaderTextPrimaryInverse
import com.rammdakk.recharge.base.theme.InputIconTextField
import com.rammdakk.recharge.base.theme.PlainText
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLargeInverse
import com.rammdakk.recharge.base.theme.TextPrimaryLargeThemed
import com.rammdakk.recharge.base.theme.TextPrimaryMedium
import com.rammdakk.recharge.base.theme.TextPrimarySmall
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.feature.activity.data.model.TimePadDataModel
import com.rammdakk.recharge.feature.activity.view.components.ActivityImage
import com.rammdakk.recharge.feature.activity.view.components.DateField
import com.rammdakk.recharge.feature.activity.view.components.NumberField
import com.rammdakk.recharge.feature.activity.view.components.TimePad
import com.rammdakk.recharge.feature.activity.view.components.WarningText
import com.rammdakk.recharge.feature.activity.view.model.ActivityExtendedInfo
import com.rammdakk.recharge.feature.activity.view.model.CalendarActivityInfo
import com.rammdakk.recharge.feature.activity.view.model.TimePad
import com.rammdakk.recharge.feature.activity.view.model.UserBookingInfo
import com.rammdakk.recharge.feature.activity.view.model.convertToActivityInfo
import com.rammdakk.recharge.feature.activity.view.model.covertToTimePad
import com.rammdakk.recharge.feature.auth.view.PhoneNumberVisualTransformation
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityInfoScreen(
    activityInfo: ActivityExtendedInfo,
    initialDate: Date,
    preSelectedId: Int? = null,
    onDateChanged: (Date) -> Unit,
    timePadList: State<List<TimePad>>,
    maxUserNumber: State<Int?>,
    onReserve: (Int, UserBookingInfo) -> Unit,
    onBackPressed: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedId: Int? by remember {
        mutableStateOf(preSelectedId)
    }

    BackHandler {
        if (selectedId != null) {
            selectedId = null
        } else {
            onBackPressed.invoke()
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ReChargeTokens.BackgroundColored.getThemedColor())
                    .padding(top = 8.dp)
                    .height(56.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        onBackPressed.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "",
                        tint = ReChargeTokens.TextPrimary.getThemedColor(),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                HeaderTextPrimary(
                    text = activityInfo.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(animationMode = MarqueeAnimationMode.Immediately)
                        .padding(horizontal = 4.dp),
                    maxLines = 1
                )
            }
        },
        containerColor = ReChargeTokens.BackgroundColored.getThemedColor(),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActivityImage(imagePath = activityInfo.imagePath, adminInfo = activityInfo.admin)
            TextPrimarySmall(
                text = activityInfo.organizationName,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            TextPrimarySmall(
                text = activityInfo.address,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
            DateField(initialDate = initialDate, onDateChanged = onDateChanged)
            if (timePadList.value.isNotEmpty()) {

                LazyRow(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    timePadList.value.let { timePad ->
                        items(timePad.size) {
                            TimePad(timePad = timePad[it]) { id ->
                                selectedId = id
                            }
                        }
                    }
                }
            }
            activityInfo.warning?.let {
                WarningText(
                    text = it, modifier = Modifier
                        .padding(top = 10.dp)
                )
            }
            activityInfo.activityDescription?.let {
                PlainText(
                    text = it,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(roundedCorner))
                        .background(ReChargeTokens.Background.getThemedColor())
                        .padding(15.dp)
                )
            }

        }
    }
    if (selectedId != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedId = null },
            sheetState = state,
            containerColor = ReChargeTokens.BackgroundContainer.getThemedColor(),
            dragHandle = null
        ) {
            SheetContent(
                selectedId,
                timePadList.value,
                maxUserNumber,
                activityInfo
            ) { id, userInfo ->
                coroutineScope.launch { state.hide() }.invokeOnCompletion {
                    if (!state.isVisible) {
                        selectedId = null
                    }
                }
                onReserve.invoke(id, userInfo)
                selectedId = null
            }
        }
    }
}

@Composable
private fun SheetContent(
    selectedId: Int?,
    timePadList: List<TimePad>,
    maxUserNumber: State<Int?>,
    activityInfo: ActivityExtendedInfo,
    onReserve: (Int, UserBookingInfo) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 32.dp)
            .navigationBarsPadding()
    ) {
        val id = selectedId ?: 0
        val timePad = runCatching { timePadList.first { it.id == id } }.getOrNull()
        if (timePad == null) {
            WarningText(text = stringResource(id = R.string.error_text))
            return
        }
        var userName by remember {
            mutableStateOf(TextFieldValue())
        }
        var phone by remember {
            mutableStateOf(TextFieldValue())
        }
        var email by remember {
            mutableStateOf(TextFieldValue())
        }
        var guestNum by remember {
            mutableIntStateOf(1)
        }
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            HeaderTextPrimaryInverse(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                text = activityInfo.name,
                textAlign = TextAlign.Center
            )
            TextPrimaryLargeInverse(
                text = "${format.format(timePad.startTime)}-${format.format(timePad.endTime)}, ${timePad.price}₽",
                modifier = Modifier
                    .padding(bottom = 30.dp, top = 4.dp)
                    .fillMaxWidth(),
            )
            activityInfo.cancellationMessage?.let { text ->
                WarningText(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = text
                )
            }
            InputIconTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                value = userName,
                fontSize = 18.sp,
                iconId = R.drawable.account_circle,
                iconColor = ReChargeTokens.TextPrimary.getThemedColor()
            ) { fieldValue ->
                userName = fieldValue
            }
            InputIconTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                value = phone,
                fontSize = 18.sp,
                iconId = R.drawable.call,
                iconColor = ReChargeTokens.TextPrimary.getThemedColor(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PhoneNumberVisualTransformation(),
            ) { fieldValue ->
                phone = fieldValue
            }
            InputIconTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                value = email,
                fontSize = 18.sp,
                iconId = R.drawable.mail,
                iconColor = ReChargeTokens.TextPrimary.getThemedColor()
            ) { fieldValue ->
                email = fieldValue
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp, start = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextPrimaryMedium(text = stringResource(id = R.string.number_of_visitors))
                NumberField(
                    maxValue = maxUserNumber.value ?: 1,
                    onNumberChanged = { guestNum = it })

            }
            TextPrimaryLargeThemed(
                text = stringResource(id = R.string.confirm),
                modifier = Modifier

                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(ReChargeTokens.Background.getThemedColor())
                    .clickable(
                        enabled = userName.text.isNotEmpty() &&
                                Patterns.EMAIL_ADDRESS
                                    .matcher(email.text)
                                    .matches() &&
                                PhoneNumberUtil
                                    .getInstance()
                                    .isPossibleNumber(phone.text, LOCALE)

                    ) {
                        onReserve.invoke(
                            id,
                            UserBookingInfo(
                                userName = userName.text,
                                email = email.text,
                                phone = phone.text,
                                reserveCount = guestNum,
                                calendarInfo = CalendarActivityInfo(
                                    activityName = activityInfo.name,
                                    startDate = timePad.startTime.time,
                                    endTime = timePad.endTime.time,
                                    locationName = activityInfo.organizationName
                                )
                            )
                        )
                    }
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
            )
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ActivityInfoScreenPreview() {
    val activity = ActivityExtendedDataModel(
        id = 1,
        imagePath = "https://lrhotel.ru/upload/resize_cache/iblock/688/767_500_2/hys84kpkx508fkmxh77gm3s1ve3afbky.JPG",
        name = "Свободное плавание",
        adminPhoneWA = "+7852367821943",
        adminTgUsername = "rammdakk",
        locationName = "Чайка бассейн",
        locationAddress = "Москва, покровскицй бульвар 11",
        activityDescription = "Открытый бассейн «Чайка», который работает круглый год. Тут вам и кафе, и лежаки, и знакомство с фридайвингом, и теплая водичка в холодное время года, и даже соляная пещера. Обратите внимание, что для посещения обязательна справка, но для удобства вы можете получить ее прямо на территории бассейна, если штатный врач будет на месте.",
        cancellationMessage = "Обратите внимание, отмена доступна не позже, чем за 12 часов",
        warning = "Нужна справка"
    ).convertToActivityInfo()!!

    val delta = 60000 * 60

    val list = listOf(
        TimePadDataModel(
            11,
            2000.00,
            Date(System.currentTimeMillis()),
            delta * 2
        ),
        TimePadDataModel(
            12,
            2000.00,
            Date(System.currentTimeMillis() + delta),
            delta * 4
        ),
        TimePadDataModel(
            13,
            2000.00,
            Date(System.currentTimeMillis() + delta * 2),
            delta * 6
        ),
        TimePadDataModel(
            14,
            2000.00,
            Date(System.currentTimeMillis() + delta * 3),
            delta * 7
        )
    ).map { it.covertToTimePad { } }
    ActivityInfoScreen(
        activityInfo = activity,
        initialDate = Date(),
        preSelectedId = null,
        {},
        timePadList = mutableStateOf(list),
        maxUserNumber = mutableIntStateOf(0),
        onBackPressed = {}, onReserve = { _, _ -> })
}

private val roundedCorner = 20.dp
private const val LOCALE = "RU"