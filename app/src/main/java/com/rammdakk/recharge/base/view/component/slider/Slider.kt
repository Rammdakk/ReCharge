package com.rammdakk.recharge.base.view.component.slider

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.base.theme.PlainTextLarge
import com.rammdakk.recharge.base.theme.ReChargeTheme

@Composable
fun Slider(
    maxValue: Float,
    minValue: Float,
    steps: Float,
    sliderPosition: State<ClosedFloatingPointRange<Float>>,
    modifier: Modifier = Modifier,
    onValueChanged: (ClosedFloatingPointRange<Float>) -> Unit
) {
    val stepsCount = (maxValue - minValue) / steps


    Column(modifier = modifier.fillMaxWidth()) {
        RangeSlider(
            value = sliderPosition.value,
            onValueChange = { range ->
                onValueChanged.invoke(range)
            },
            valueRange = minValue..maxValue,
            steps = stepsCount.toInt(),
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            )
        )
    }
}

@Composable
fun SliderWithTextInfo(
    modifier: Modifier = Modifier,
    maxValue: Float,
    minValue: Float,
    steps: Float,
    sliderPosition: State<ClosedFloatingPointRange<Float>>,
    colors: SliderColors = SliderDefaults.colors(
        activeTickColor = Color.Transparent,
        inactiveTickColor = Color.Transparent
    ),
    toTextTransformation: (Float) -> String = { it.toString() },
    onValueChanged: (ClosedFloatingPointRange<Float>) -> Unit,
) {

    val stepsCount = (maxValue - minValue) / steps

    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            PlainTextLarge(text = toTextTransformation(sliderPosition.value.start))
            PlainTextLarge(text = toTextTransformation(sliderPosition.value.endInclusive))
        }
        RangeSlider(
            value = sliderPosition.value,
            onValueChange = { range ->
                onValueChanged.invoke(range)
            },
            valueRange = minValue..maxValue,
            steps = stepsCount.toInt(),
            modifier = Modifier.fillMaxWidth(),
            colors = colors
        )
    }
}

@Preview
@Composable
fun PreviewTimeSlider() {
    ReChargeTheme {
        val sliderPosition = remember { mutableStateOf(0f..24 * 60f) }
        Column(modifier = Modifier.padding(10.dp)) {
            Slider(24 * 60f, 0f, 15f, sliderPosition) {
                sliderPosition.value = it
            }
            Text(text = sliderPosition.value.toString())
        }
    }
}

@Preview
@Composable
fun PreviewTimeTextSlider() {
    ReChargeTheme {
        val sliderPosition = remember { mutableStateOf(0f..24 * 60f) }
        Column(modifier = Modifier.padding(10.dp)) {
            SliderWithTextInfo(Modifier, 24 * 60f, 0f, 15f, sliderPosition) {
                sliderPosition.value = it
            }
        }
    }
}
