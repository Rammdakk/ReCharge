package com.rammdakk.recharge.feature.activityList.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.HeaderTextPrimary
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun SearchRow(
    title: String,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp, start = 8.dp, end = 8.dp)
            .height(50.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onBackPressed.invoke()
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "",
                tint = ReChargeTokens.TextPrimary.getThemedColor(),
                modifier = Modifier.fillMaxSize()
            )
        }
        HeaderTextPrimary(
            text = title,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 4.dp)
        )
        Icon(
            painterResource(id = R.drawable.search),
            "",
            modifier = Modifier
                .clip(CircleShape)
                .clickable { }
                .background(Color.Transparent)
                .fillMaxHeight()
                .padding(10.dp)
                .aspectRatio(1f, true),
            ReChargeTokens.BackgroundInverse.getThemedColor()
        )
    }
}

@Preview
@Composable
fun SearchRowPreview() {
    SearchRow("Активность", { })
}