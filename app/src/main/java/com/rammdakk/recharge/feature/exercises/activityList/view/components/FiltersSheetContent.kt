package com.rammdakk.recharge.feature.exercises.activityList.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.view.component.slider.SliderWithTextInfo
import kotlin.math.roundToInt

@Composable
fun FiltersSheetContent(
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

    FiltersSheetContent(time.value, price.value) { selectedTime, selectedPrice ->
        time.value = selectedTime
        price.value = selectedPrice
    }
}