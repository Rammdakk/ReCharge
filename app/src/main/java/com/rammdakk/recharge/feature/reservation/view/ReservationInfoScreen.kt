package com.rammdakk.recharge.feature.reservation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.HeaderTextPrimary
import com.rammdakk.recharge.base.theme.PlainText
import com.rammdakk.recharge.base.theme.PlainTextLarge
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.TextPrimaryMedium
import com.rammdakk.recharge.base.theme.TextPrimarySmall
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.activity.view.components.ActivityImage
import com.rammdakk.recharge.feature.activity.view.components.WarningText
import com.rammdakk.recharge.feature.activity.view.model.ActivityExtendedInfo
import com.rammdakk.recharge.feature.reservation.view.components.CancelReservationDialog
import com.rammdakk.recharge.feature.reservation.view.model.ReservationInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationInfoScreen(
    activityInfo: ActivityExtendedInfo,
    reservationInfo: ReservationInfo,
    onBackPressed: () -> Unit,
    onCancelClick: (Int) -> Unit
) {
    val state = rememberBottomSheetScaffoldState(
        rememberModalBottomSheetState(confirmValueChange = { sheetValue -> sheetValue != SheetValue.Hidden })
    )
    LaunchedEffect(Unit) {
        state.bottomSheetState.partialExpand()
    }

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    val scope = rememberCoroutineScope()

    var isCancelDialogVisible by remember {
        mutableStateOf(false)
    }

    BottomSheetScaffold(
        scaffoldState = state,
        sheetPeekHeight = with(LocalDensity.current) {
            26.sp.toDp()
        } + 40.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(enabled = state.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
                        scope.launch {
                            state.bottomSheetState.expand()
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeaderTextPrimary(
                    text = stringResource(id = R.string.reservation_code_header),
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 20.dp),
                    textAlign = TextAlign.Center
                )
                PlainTextLarge(
                    text = stringResource(id = R.string.reservation_code_text),
                    modifier = Modifier.padding(top = 80.dp),
                    textAlign = TextAlign.Center,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(40.dp)
                        .background(
                            ReChargeTokens.Background.getThemedColor(),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextPrimaryLarge(
                        text = activityInfo.organizationName,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                    reservationInfo.time?.let {
                        TextPrimarySmall(
                            text = it,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                    TextPrimarySmall(
                        text = stringResource(
                            id = R.string.reservation_code_codetext,
                            reservationInfo.accessCode
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(top = 16.dp, bottom = 20.dp)
                            .aspectRatio(1f),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("$QrUrl${reservationInfo.accessCode}")
                            .crossfade(true)
                            .build(),
                        contentDescription = reservationInfo.accessCode,
                        contentScale = ContentScale.Crop
                    )
                }
            }

        },
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
                    text = stringResource(id = R.string.reservation_header),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Start
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActivityImage(imagePath = activityInfo.imagePath, adminInfo = activityInfo.admin)
            TextPrimaryMedium(
                text = activityInfo.name,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            TextPrimarySmall(
                text = activityInfo.organizationName,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clickable { clipboardManager.setText(AnnotatedString(activityInfo.address)) }
            )
            TextPrimarySmall(
                text = activityInfo.address,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clickable { clipboardManager.setText(AnnotatedString(activityInfo.address)) }
            )
            activityInfo.warning?.let {
                WarningText(
                    text = it, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
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
            TextPrimaryMedium(
                text = stringResource(id = R.string.reservation_cancel),
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        isCancelDialogVisible = true
                    }
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                textAlign = TextAlign.Center,
            )

        }

    }
    if (isCancelDialogVisible) {
        CancelReservationDialog(onDismissRequest = { isCancelDialogVisible = false }) {
            reservationInfo.reservationId?.let { onCancelClick.invoke(it) }
            isCancelDialogVisible = false
        }
    }
}

private val roundedCorner = 20.dp
private const val QrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data="