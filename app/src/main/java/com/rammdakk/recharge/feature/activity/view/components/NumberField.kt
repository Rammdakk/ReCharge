package com.rammdakk.recharge.feature.activity.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimarySmall
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun NumberField(
    modifier: Modifier = Modifier,
    maxValue: Int,
    onNumberChanged: (Int) -> Unit
) {
    var currentVal by remember { mutableIntStateOf(1) }

    Row(
        modifier = modifier
            .wrapContentSize()
            .padding(2.dp)
            .clip(RoundedCornerShape(50))
            .background(ReChargeTokens.Background.getThemedColor()),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var height by remember {
            mutableIntStateOf(0)
        }

        IconButton(
            onClick = {
                if (currentVal > 1) {
                    currentVal--
                    onNumberChanged(currentVal)
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.minus), contentDescription = "Decrease",
                tint = ReChargeTokens.Background.getThemedColor(),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(50))
                    .background(ReChargeTokens.BackgroundColored.getThemedColor())
                    .padding(8.dp)
            )
        }

        TextPrimarySmall(
            text = currentVal.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 12.dp)
                .onSizeChanged {
                    height = it.height
                }
        )

        IconButton(
            onClick = {
                if (currentVal < maxValue) {
                    currentVal++
                    onNumberChanged(currentVal)
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus), contentDescription = "Decrease",
                tint = ReChargeTokens.Background.getThemedColor(),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(50))
                    .background(ReChargeTokens.BackgroundColored.getThemedColor())
                    .padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun NumPreview() {
    NumberField(maxValue = 4) {}
}