package com.rammdakk.recharge.activity.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.rammdakk.recharge.base.theme.TextPrimarySmall
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DateField(
    initialDate: Date,
    onDateChanged: (Date) -> Unit
) {
    var date by remember { mutableStateOf(initialDate) }

    Row(
        modifier = Modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                date = Calendar.getInstance().apply {
                    time = date
                    add(Calendar.DATE, -1)
                }.time
                onDateChanged(date)
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Day",
                tint = Color.Black,
                modifier = Modifier.fillMaxSize(0.8f)
            )
        }

        TextPrimarySmall(
            text = SimpleDateFormat("d MMMM", Locale.getDefault()).format(date),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentSize()
        )

        IconButton(
            onClick = {
                date = Calendar.getInstance().apply {
                    time = date
                    add(Calendar.DATE, 1)
                }.time
                onDateChanged(date)
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Next Day",
                tint = Color.Black,
                modifier = Modifier.fillMaxSize(0.8f)
            )
        }
    }
}

@Preview
@Composable
fun DatePreview() {
    DateField(initialDate = Date(), onDateChanged = {})
}