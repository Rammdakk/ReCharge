package com.rammdakk.recharge.activity.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryMediumInverse
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun WarningText(modifier: Modifier = Modifier, text: String) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(0.9f)
            .clip(RoundedCornerShape(20.dp))
            .background(ReChargeTokens.BackgroundInfo.getThemedColor()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.warning),
            contentDescription = "",
            colorFilter = ColorFilter.tint(ReChargeTokens.TextAttention.getThemedColor()),
            modifier = Modifier
                .padding(all = 4.dp)
                .size(36.dp)
                .clip(RoundedCornerShape(50))
                .background(ReChargeTokens.Background.getThemedColor())
                .padding(vertical = 5.dp)
        )
        TextPrimaryMediumInverse(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 14.dp),
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
fun WarningTextPreview() {
    WarningText(text = "Нужна справка")
}

@Preview
@Composable
fun WarningTextPreviewLarge() {
    WarningText(text = "Нужна справка dsh Нужна havbdj справка Нужна справка dsh Нужна havbdj справка Нужна справка dsh Нужна havbdj справка")
}