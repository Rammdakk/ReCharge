package com.rammdakk.recharge.feature.calendar.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.HeaderTextPrimary
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.calendar.view.components.calendar.CalendarHeader
import com.rammdakk.recharge.feature.calendar.view.components.calendar.CalendarState
import com.rammdakk.recharge.feature.calendar.view.components.calendar.CalendarWidget
import com.rammdakk.recharge.feature.calendar.view.components.reservation.ReservationCell
import com.rammdakk.recharge.feature.calendar.view.model.ReservationModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    calendarState: CalendarState,
    reservationList: State<List<ReservationModel>>,
    navigator: DestinationsNavigator
) {
    val listState = rememberLazyListState()

    LaunchedEffect(calendarState.currentMoth.value) {
        if (listState.firstVisibleItemIndex > 0) {
            listState.animateScrollToItem(1)
        } else {
            listState.animateScrollToItem(0)
        }
    }
    Scaffold(
        containerColor = ReChargeTokens.Background.getThemedColor(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HeaderTextPrimary(
                    text = stringResource(id = R.string.calendar_header),
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
                .padding(horizontal = 16.dp)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalendarHeader(
                yearMonth = calendarState.currentMoth.value,
                onMonthChanged = calendarState.onMonthChanged,
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState
            ) {
                CalendarWidget(
                    calendarState = calendarState,
                    modifier = Modifier.padding(top = 0.dp, bottom = 20.dp),
                )
                stickyHeader(key = 123) {
                    TextPrimaryLarge(
                        text = stringResource(id = R.string.calendar_current_month),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ReChargeTokens.Background.getThemedColor())
                            .padding(bottom = 4.dp, start = 4.dp)
                    )
                }
                items(reservationList.value) {
                    ReservationCell(it, navigator)
                }
                items(reservationList.value) {
                    ReservationCell(it, navigator)
                }
                items(reservationList.value) {
                    ReservationCell(it, navigator)
                }
            }
        }
    }
}
