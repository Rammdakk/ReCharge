package com.rammdakk.recharge.activity.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.activity.data.model.ActivityExtendedDataModel
import com.rammdakk.recharge.activity.data.model.TimePadDataModel
import com.rammdakk.recharge.activity.view.components.ActivityImage
import com.rammdakk.recharge.activity.view.components.DateField
import com.rammdakk.recharge.activity.view.components.TimePad
import com.rammdakk.recharge.activity.view.components.WarningText
import com.rammdakk.recharge.activity.view.model.ActivityExtendedInfo
import com.rammdakk.recharge.activity.view.model.TimePad
import com.rammdakk.recharge.activity.view.model.convertToActivityInfo
import com.rammdakk.recharge.activity.view.model.covertToTimePad
import com.rammdakk.recharge.base.theme.HeaderTextPrimary
import com.rammdakk.recharge.base.theme.HeaderTextPrimaryInverse
import com.rammdakk.recharge.base.theme.InputIconTextField
import com.rammdakk.recharge.base.theme.PlainText
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLargeInverse
import com.rammdakk.recharge.base.theme.TextPrimarySmall
import com.rammdakk.recharge.base.theme.getThemedColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityInfoScreen(
    activityInfo: ActivityExtendedInfo,
    onDateChanged: (Date) -> Unit,
    timePadList: State<List<TimePad>>,
    navigator: DestinationsNavigator
) {
    val state = rememberBottomSheetScaffoldState(
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    )

    var selectedId: Int? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(selectedId) {
        if (selectedId == null) {
            state.bottomSheetState.hide()
        } else {
            state.bottomSheetState.expand()
        }
    }

    val height = LocalConfiguration.current.screenHeightDp


    BottomSheetScaffold(
        scaffoldState = state,
        sheetContent = {
            SheetContent(height, selectedId, timePadList.value, activityInfo)
        },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        navigator.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "",
                        tint = ReChargeTokens.TextPrimary.getThemedColor(),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                HeaderTextPrimary(
                    text = activityInfo.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                )
            }
        },
        containerColor = ReChargeTokens.BackgroundColored.getThemedColor(),
        sheetDragHandle = null,
        sheetContainerColor = ReChargeTokens.BackgroundContainer.getThemedColor(),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
            DateField(initialDate = Date(), onDateChanged = onDateChanged)
            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 10.dp)
            ) {
                timePadList.value.let { timepad ->
                    items(timepad.size) {
                        TimePad(timePad = timepad[it]) {
                            selectedId = it
                        }
                    }
                }
            }
            activityInfo.activityDescription?.let {
                PlainText(
                    text = it,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(roundedCorner))
                        .background(ReChargeTokens.Background.getThemedColor())
                        .padding(15.dp)
                )
            }
            activityInfo.warning?.let {
                WarningText(text = it)
            }

        }

    }
}

@Composable
private fun SheetContent(
    height: Int,
    selectedId: Int?,
    timePadList: List<TimePad>,
    activityInfo: ActivityExtendedInfo
) {
    var selectedId1 = selectedId
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height((height * 2 / 3).dp)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val id = selectedId1 ?: 0
        val timePad = runCatching { timePadList.first { it.id == id } }.getOrNull()
        if (timePad == null) {
            selectedId1 = null
        }
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
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        HeaderTextPrimaryInverse(
            modifier = Modifier.padding(vertical = 8.dp),
            text = activityInfo.name,
            textAlign = TextAlign.Center
        )
        TextPrimaryLargeInverse(
            text = "${format.format(timePad.startTime)}-${format.format(timePad.endTime)}, ${timePad.price}₽",
            modifier = Modifier.padding(bottom = 8.dp, top = 4.dp),
        )
        activityInfo.cancellationMessage?.let { text ->
            WarningText(
                modifier = Modifier.padding(vertical = 8.dp),
                text = text
            )
        }
        InputIconTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 8.dp),
            value = userName,
            fontSize = 18.sp,
            iconId = R.drawable.account_circle
        ) { fieldValue ->
            userName = fieldValue
        }
        InputIconTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 8.dp),
            value = phone,
            fontSize = 18.sp,
            iconId = R.drawable.call,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        ) { fieldValue ->
            phone = fieldValue
        }
        InputIconTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 8.dp),
            value = email,
            fontSize = 18.sp,
            iconId = R.drawable.mail
        ) { fieldValue ->
            email = fieldValue
        }
    }

}


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ActivityInfoScreenPreview() {
    val activity = ActivityExtendedDataModel(
        id = 1,
        imagePath = "https://riamo.ru/files/image/04/54/86/gallery!alf.jpg",
        name = "Pool",
        adminPhoneWA = "+7852367821943",
        adminTgUsername = "rammdakk",
        locationName = "Чайка бассейн",
        locationAddress = "Москва, покровскицй бульвар 11",
        activityDescription = "Открытый бассейн «Чайка», который работает круглый год. Тут вам и кафе, и лежаки, и знакомство с фридайвингом, и теплая водичка в холодное время года, и даже соляная пещера. Обратите внимание, что для посещения обязательна справка, но для удобства вы можете получить ее прямо на территории бассейна, если штатный врач будет на месте.",
        cancellationMessage = "Обратите внимание, отмена доступна не позже, чем за 12 часов",
        warning = "Нужна справка"
    ).convertToActivityInfo()

    val delta = 60000 * 60

    val list = listOf(
        TimePadDataModel(
            11,
            2000.00,
            Date(System.currentTimeMillis()),
            Date(System.currentTimeMillis() + delta)
        ),
        TimePadDataModel(
            12,
            2000.00,
            Date(System.currentTimeMillis() + delta),
            Date(System.currentTimeMillis() + delta * 2)
        ),
        TimePadDataModel(
            13,
            2000.00,
            Date(System.currentTimeMillis() + delta * 2),
            Date(System.currentTimeMillis() + delta * 3)
        ),
        TimePadDataModel(
            14,
            2000.00,
            Date(System.currentTimeMillis() + delta * 3),
            Date(System.currentTimeMillis() + delta * 4)
        )
    ).map { it.covertToTimePad { } }
//    ActivityInfoScreen(activityInfo = activity, {}, timePadList = mutableStateOf(list))
}

private val roundedCorner = 20.dp