package com.rammdakk.recharge.feature.activity.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.base.theme.PlainText
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.activity.view.model.TimePad
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TimePad(timePad: TimePad, onClick: (Int) -> Unit) {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .widthIn(90.dp, Dp.Unspecified)
            .height(55.dp)
            .clip(RoundedCornerShape(roundedCorner))
            .clickable {
                timePad.onClick.invoke(timePad.id)
                onClick.invoke(timePad.id)
            }
            .background(ReChargeTokens.Background.getThemedColor())
            .padding(horizontal = 6.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlainText(
            text = "${format.format(timePad.startTime)}-${format.format(timePad.endTime)}",
            textAlign = TextAlign.Center,
        )
        PlainText(
            text = "${timePad.price}₽",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(ReChargeTokens.BackgroundColored.getThemedColor())
                .padding(horizontal = 5.dp, vertical = 3.dp)
                .fillMaxWidth()
        )
    }
}

private val roundedCorner = 12.dp