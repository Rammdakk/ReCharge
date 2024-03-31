package com.rammdakk.recharge.feature.exercises.view.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryMedium
import com.rammdakk.recharge.base.theme.TextPrimaryMediumInverse
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun TabCell(
    modifier: Modifier = Modifier,
    exercisesName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val cellWidth = LocalConfiguration.current.screenWidthDp.dp * 0.4f
    val color =
        if (isSelected) ReChargeTokens.Background.getThemedColor() else ReChargeTokens.BackgroundColored.getThemedColor()
    val backgroundColor = animateColorAsState(targetValue = color, label = "backgroundColor")
    val newModifier = modifier
        .clip(RoundedCornerShape(50))
        .clickable { onClick.invoke() }
        .border(
            width = 2.dp,
            color = ReChargeTokens.BackgroundColored.getThemedColor(),
            shape = RoundedCornerShape(50)
        )
        .width(cellWidth)
        .background(backgroundColor.value)
        .padding(vertical = 8.dp, horizontal = 8.dp)
    if (isSelected) {
        TextPrimaryMedium(
            modifier = newModifier,
            text = exercisesName,
            textAlign = TextAlign.Center
        )
    } else {
        TextPrimaryMediumInverse(
            modifier = newModifier,
            text = exercisesName,
            textAlign = TextAlign.Center
        )
    }

}

@Preview
@Composable
fun TabCellPreview() {
    Column {
        TabCell(exercisesName = "exercisesTab", isSelected = true, onClick = {})
        TabCell(exercisesName = "exercisesTab", isSelected = false, onClick = {})
    }

}