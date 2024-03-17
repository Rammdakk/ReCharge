package com.rammdakk.recharge.feature.profile.view.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    minAge: Int = 0,
    initialVale: Long?,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialVale)

    val date = Calendar.getInstance().apply {
        add(Calendar.YEAR, -minAge)
    }
    val selectedDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()

    var visibleDialog by remember {
        mutableStateOf(false)
    }

    if (visibleDialog) {
        AlertDialog(
            onDismissRequest = { visibleDialog = false },
            title = {
                Text(
                    text = stringResource(
                        id = R.string.profile_calendar_error_dialog_title
                    ),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.profile_calendar_error_dialog_text,
                    ),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        visibleDialog = false
                    }
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.profile_calendar_error_dialog_confirm,
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            },
        )
    }

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedDate < date.timeInMillis) {
                        onDateSelected(selectedDate)
                    } else {
                        visibleDialog = true
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = ReChargeTokens.BackgroundColored.getThemedColor())
            ) {
                Text(
                    text = stringResource(
                        id = R.string.profile_calendar_confirm,
                    ),
                    textAlign = TextAlign.Center
                )

            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = ReChargeTokens.BackgroundColored.getThemedColor())
            ) {
                Text(
                    text = stringResource(
                        id = R.string.profile_calendar_cancel,
                    ),
                    textAlign = TextAlign.Center
                )
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = ReChargeTokens.BackgroundColored.getThemedColor(),
                selectedYearContainerColor = ReChargeTokens.BackgroundColored.getThemedColor(),
            )
        )
    }
}
