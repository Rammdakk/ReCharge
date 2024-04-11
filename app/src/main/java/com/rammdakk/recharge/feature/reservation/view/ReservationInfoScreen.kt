package com.rammdakk.recharge.feature.reservation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.HeaderTextPrimary
import com.rammdakk.recharge.base.theme.PlainText
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryMedium
import com.rammdakk.recharge.base.theme.TextPrimarySmall
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.activity.view.components.ActivityImage
import com.rammdakk.recharge.feature.activity.view.components.WarningText
import com.rammdakk.recharge.feature.activity.view.model.ActivityExtendedInfo
import com.rammdakk.recharge.feature.reservation.view.model.ReservationInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationInfoScreen(
    activityInfo: ActivityExtendedInfo,
    reservationInfo: ReservationInfo,
    onBackPressed: () -> Unit
) {
    val state = rememberBottomSheetScaffoldState(
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    )

    var selectedId: Int? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(state.bottomSheetState.isVisible) {
        if (!state.bottomSheetState.isVisible) {
            selectedId = null
        }
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
                        imageVector = Icons.Default.KeyboardArrowLeft,
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
            )
            TextPrimarySmall(
                text = activityInfo.address,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
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
            activityInfo.warning?.let {
                WarningText(text = it)
            }

        }

    }
}

private val roundedCorner = 20.dp