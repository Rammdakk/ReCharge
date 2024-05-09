package com.rammdakk.recharge.feature.activity.view.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTheme
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun AddToCalendarDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    ReChargeTheme {
        AlertDialog(
            title = {
                Text(text = stringResource(id = R.string.activity_add_to_calendar_title))
            },
            text = {
                Text(text = stringResource(id = R.string.activity_add_to_calendar))
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = com.rammdakk.recharge.base.theme.ReChargeTokens.BackgroundContainer.getThemedColor())
                ) {
                    Text(stringResource(id = R.string.activity_add_to_calendar_add))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = com.rammdakk.recharge.base.theme.ReChargeTokens.BackgroundContainer.getThemedColor())
                ) {
                    Text(stringResource(id = R.string.activity_add_to_calendar_cancel))
                }
            },
            containerColor = com.rammdakk.recharge.base.theme.ReChargeTokens.Background.getThemedColor(),

            )
    }
}
