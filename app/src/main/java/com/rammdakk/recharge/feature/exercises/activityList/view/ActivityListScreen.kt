package com.rammdakk.recharge.feature.exercises.activityList.view

import android.util.Log
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.view.component.slider.SliderWithTextInfo
import com.rammdakk.recharge.feature.exercises.activityList.view.components.ActivityCell
import com.rammdakk.recharge.feature.exercises.activityList.view.components.AppBar
import com.rammdakk.recharge.feature.exercises.activityList.view.components.DateField
import com.rammdakk.recharge.feature.exercises.activityList.view.model.ActivityInfo
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.math.roundToInt

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
    val saver: Saver<ClosedFloatingPointRange<Float>, String> =
        Saver(
            save = { range -> "${range.start},${range.endInclusive}" },
            restore = { saved ->
                val (start, endInclusive) = saved.split(",").map { it.toFloat() }
                start..endInclusive
            }
        )

    val coroutineScope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState()
    var searchText by remember {
        mutableStateOf("")
    }
    var isBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    val time = rememberSaveable(stateSaver = saver) { mutableStateOf(0f..24 * 60f) }
    val price = rememberSaveable(stateSaver = saver) { mutableStateOf(0f..100000f) }

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
                (it.name.lowercase().startsWith(searchText) || it.organizationName.lowercase()
                    .startsWith(searchText)) && (time.value.start <= it.startTime && time.value.endInclusive >= it.startTime)
                        && (price.value.start <= it.price && price.value.endInclusive >= it.price)
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
            containerColor = ReChargeTokens.Background.getThemedColor(),
            onDismissRequest = { isBottomSheetVisible = false },
            sheetState = state,
            dragHandle = null
        ) {
            SheetContent(time.value, price.value) { selectedTime, selectedPrice ->
                Log.d("Ramil", selectedTime.endInclusive.toString())
                time.value = selectedTime
                price.value = selectedPrice
                coroutineScope.launch { state.hide() }.invokeOnCompletion {
                    if (!state.isVisible) {
                        isBottomSheetVisible = false
                    }
                }
            }
        }
    }
}

@Composable
private fun SheetContent(
    timeInitialValue: ClosedFloatingPointRange<Float>,
    priceInitialValue: ClosedFloatingPointRange<Float>,
    onClickAction: (ClosedFloatingPointRange<Float>, ClosedFloatingPointRange<Float>) -> Unit
) {
    val colors = SliderDefaults.colors(
        activeTickColor = Color.Transparent,
        inactiveTickColor = Color.Transparent,
        activeTrackColor = ReChargeTokens.BackgroundColored.getThemedColor(),
        thumbColor = ReChargeTokens.BackgroundContainer.getThemedColor()
    )
    val time = remember { mutableStateOf(timeInitialValue) }
    val price = remember { mutableStateOf(priceInitialValue) }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
    ) {
        TextPrimaryLarge(
            text = stringResource(id = R.string.exercise_filters),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        SliderWithTextInfo(
            modifier = Modifier.padding(vertical = 8.dp),
            maxValue = (24 * 60f),
            minValue = 0f,
            steps = 15f,
            sliderPosition = time,
            toTextTransformation = ::toTime,
            colors = colors
        ) {
            time.value = it
        }
        SliderWithTextInfo(
            modifier = Modifier.padding(vertical = 8.dp),
            maxValue = 100000f,
            minValue = 0f,
            steps = 1000f,
            sliderPosition = price,
            toTextTransformation = ::toCurrency,
            colors = colors
        ) {
            price.value = it
        }
        TextPrimaryLarge(
            text = stringResource(id = R.string.exercise_apply),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(50))
                .background(ReChargeTokens.BackgroundColored.getThemedColor())
                .clickable { onClickAction.invoke(time.value, price.value) }
                .padding(vertical = 12.dp),
            textAlign = TextAlign.Center,
        )
    }
}

private fun toTime(value: Float): String {
    val time = value.toInt()
    return "${String.format("%02d", time / 60)}:${String.format("%02d", time % 60)}"
}

private fun toCurrency(value: Float): String {
    return "${value.roundToInt()}₽"
}

@Preview
@Composable
private fun SheetPreview() {
    val time = remember { mutableStateOf(0f..24 * 60f) }
    val price = remember { mutableStateOf(0f..100000f) }

    SheetContent(time.value, price.value) { selectedTime, selectedPrice ->
        time.value = selectedTime
        price.value = selectedPrice
    }
}
