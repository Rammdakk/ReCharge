package com.rammdakk.recharge.feature.exercises.activityList.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryMedium
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.view.component.slider.SliderWithTextInfo
import com.rammdakk.recharge.feature.exercises.activityList.view.components.ActivityCell
import com.rammdakk.recharge.feature.exercises.activityList.view.components.AppBar
import com.rammdakk.recharge.feature.exercises.activityList.view.components.DateField
import com.rammdakk.recharge.feature.exercises.activityList.view.model.ActivityInfo
import java.util.Date

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActivityListScreen(
    title: String,
    date: State<Date>,
    activities: State<List<ActivityInfo>>,
    updateDate: (Date) -> Unit,
    navigator: DestinationsNavigator,
    onBackPressed: () -> Unit
) {
    val state = rememberModalBottomSheetState()
    var searchText by remember {
        mutableStateOf("")
    }
    var isBottomSheetVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isBottomSheetVisible) {
        if (isBottomSheetVisible) {
//            state.expand()
        } else {
            state.hide()
        }
    }
    val time = remember { mutableStateOf(0f..24 * 60f) }
    val price = remember { mutableStateOf(0f..100000f) }
    Scaffold(
        containerColor = ReChargeTokens.Background.getThemedColor(),
        topBar = {
            AppBar(title = title, onSearch = { searchText = it }, onBackPressed = onBackPressed)
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ReChargeTokens.Background.getThemedColor()),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DateField(date) {
                        updateDate.invoke(it)
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        tint = ReChargeTokens.BackgroundInverse.getThemedColor(),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(24.dp)
                            .clickable { isBottomSheetVisible = true })
                }
            }
            items(activities.value.filter {
                it.name.lowercase().startsWith(searchText) || it.organizationName.lowercase()
                    .startsWith(searchText)
            }) { activity ->
                ActivityCell(activityInfo = activity, navigator = navigator)
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            sheetState = state
        ) {
            SheetContent(timeState = time, priceState = price)
        }
    }
}

@Composable
private fun SheetContent(
    timeState: MutableState<ClosedFloatingPointRange<Float>>,
    priceState: MutableState<ClosedFloatingPointRange<Float>>,
) {
    Column {
        TextPrimaryMedium(text = stringResource(id = R.string.exercise_filters))
        SliderWithTextInfo(
            modifier = Modifier,
            maxValue = (24 * 60f - 1),
            minValue = 0f,
            steps = 15f,
            sliderPosition = timeState,
            toTextTransformation = ::toTime
        ) {
            timeState.value = it
        }
        SliderWithTextInfo(
            modifier = Modifier,
            maxValue = 24 * 60f,
            minValue = 0f,
            steps = 15f,
            sliderPosition = priceState,
        ) {
            timeState.value = it
        }
    }
}

private fun toTime(value: Float): String {
    val time = value.toInt()
    return "${time / 60}:${time % 60}"
}