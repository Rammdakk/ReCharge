package com.rammdakk.recharge.base.view.component.dialog

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun LoadingDialog(
    isShowingDialog: Boolean,
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false
) {
    if (isShowingDialog) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            )
        ) {
            CircularProgressIndicator(
                modifier = Modifier,
                color = ReChargeTokens.BackgroundContainer.getThemedColor()
            )
        }
    }
}