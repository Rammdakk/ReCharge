package com.rammdakk.recharge.feature.calendar.view.components.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.base.theme.PlainText
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.TextPrimaryMedium
import com.rammdakk.recharge.base.theme.getThemedColor
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
//@Composable
fun LazyListScope.CalendarWidget(
    modifier: Modifier = Modifier,
    calendarState: CalendarState,
) {
    val daysName =
        DayOfWeek.entries.map { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }

    val yearMonth by calendarState.currentMoth

    val dates: List<Date> = yearMonth.getDayOfMonthStartingFromMonday()
        .map { Date.from(it.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) }
    item {
        Column(
            modifier = modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(24.dp))
                .background(ReChargeTokens.BackgroundColored.getThemedColor())
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Row {
                repeat(daysName.size) {
                    val item = daysName[it]
                    DayItem(item, modifier = Modifier.weight(1f))
                }
            }
            Content(
                currentMont = yearMonth.monthValue - 1,
                dates = dates,
                onClick = calendarState.onDateSelected
            )
        }
    }
}

@Composable
fun CalendarHeader(
    yearMonth: YearMonth,
    onMonthChanged: (YearMonth) -> Unit,
) {
    Row {
        IconButton(onClick = {
            onMonthChanged.invoke(yearMonth.minusMonths(1))
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "",
                tint = ReChargeTokens.TextPrimary.getThemedColor(),
            )
        }
        TextPrimaryLarge(
            text = yearMonth.getDisplayName(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = {
            onMonthChanged.invoke(yearMonth.plusMonths(1))
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "",
                tint = ReChargeTokens.TextPrimary.getThemedColor(),
            )
        }
    }
}

@Composable
private fun DayItem(day: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        TextPrimaryMedium(
            text = day,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}

@Composable
private fun Content(
    currentMont: Int,
    dates: List<Date>,
    onClick: (Date) -> Unit
) {
    Column {
        var index = 0
        repeat(6) {
            Row {
                repeat(7) {
                    val item = if (index < dates.size) dates[index] else null
                    ContentItem(
                        date = item.takeIf { it?.month == currentMont },
                        modifier = Modifier.weight(1f),
                        onClick = onClick
                    )
                    index++
                }
            }
        }
    }
}

@Composable
private fun ContentItem(
    date: Date?,
    onClick: (Date) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color.Transparent
            )
            .clickable {
                date?.let(onClick)
            }
    ) {
        PlainText(
            text = date?.date?.toString().orEmpty(),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}


fun YearMonth.getDayOfMonthStartingFromMonday(): List<LocalDate> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val firstMondayOfMonth = firstDayOfMonth.with(DayOfWeek.MONDAY)
    val firstDayOfNextMonth = firstDayOfMonth.plusMonths(1)

    return generateSequence(firstMondayOfMonth) { it.plusDays(1) }
        .takeWhile { it.isBefore(firstDayOfNextMonth) }
        .toList()
}


fun YearMonth.getDisplayName(): String {
    return "${month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())} $year"
}


@Preview
@Composable
fun CalendarPreview() {
    val calendar = remember {
        mutableStateOf(YearMonth.now())
    }
    val state = CalendarState(currentMoth = calendar, onMonthChanged = {
        calendar.value = it
    },
        onDateSelected = {})
//    CalendarWidget(calendarState = state)
}