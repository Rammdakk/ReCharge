package com.rammdakk.recharge.feature.exercises.activityList.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.exercises.activityList.view.components.ActivityCell
import com.rammdakk.recharge.feature.exercises.activityList.view.components.AppBar
import com.rammdakk.recharge.feature.exercises.activityList.view.components.DateField
import com.rammdakk.recharge.feature.exercises.activityList.view.components.FiltersSheetContent
import com.rammdakk.recharge.feature.exercises.activityList.view.model.ActivityInfo
import kotlinx.coroutines.launch
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

    var isFiltersBottomSheetVisible by remember { mutableStateOf(false) }
    var isSortBottomSheetVisible by remember { mutableStateOf(false) }

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
                            .clickable { isFiltersBottomSheetVisible = true })
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
    if (isFiltersBottomSheetVisible) {
        ModalBottomSheet(
            containerColor = ReChargeTokens.Background.getThemedColor(),
            onDismissRequest = { isFiltersBottomSheetVisible = false },
            sheetState = state,
            dragHandle = null
        ) {
            FiltersSheetContent(time.value, price.value) { selectedTime, selectedPrice ->
                Log.d("Ramil", selectedTime.endInclusive.toString())
                time.value = selectedTime
                price.value = selectedPrice
                coroutineScope.launch { state.hide() }.invokeOnCompletion {
                    if (!state.isVisible) {
                        isFiltersBottomSheetVisible = false
                    }
                }
            }
        }
    }
    if (isFiltersBottomSheetVisible) {
        ModalBottomSheet(
            containerColor = ReChargeTokens.Background.getThemedColor(),
            onDismissRequest = { isFiltersBottomSheetVisible = false },
            sheetState = state,
            dragHandle = null
        ) {
            FiltersSheetContent(time.value, price.value) { selectedTime, selectedPrice ->
                Log.d("Ramil", selectedTime.endInclusive.toString())
                time.value = selectedTime
                price.value = selectedPrice
                coroutineScope.launch { state.hide() }.invokeOnCompletion {
                    if (!state.isVisible) {
                        isFiltersBottomSheetVisible = false
                    }
                }
            }
        }
    }
}

