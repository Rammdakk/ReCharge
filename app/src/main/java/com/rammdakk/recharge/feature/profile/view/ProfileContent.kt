package com.rammdakk.recharge.feature.profile.view

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.base.theme.setSystemBarsColors
import com.rammdakk.recharge.base.view.component.error.Error
import com.rammdakk.recharge.feature.auth.view.AuthActivity

@Destination
@Composable
fun ProfileContent(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val uiState by viewModel.profileState

    setSystemBarsColors(
        statusBarColor = ReChargeTokens.Background.getThemedColor(),
        navBarColor = ReChargeTokens.Background.getThemedColor()
    )

    val context = LocalContext.current

    LaunchedEffect(viewModel.isLoggedOut) {
        if (viewModel.isLoggedOut.value) {
            val intent = Intent(context, AuthActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    Crossfade(
        modifier = Modifier
            .background(ReChargeTokens.Background.getThemedColor())
            .padding(horizontal = 16.dp),
        targetState = uiState,
        label = ""
    ) { state ->
        when (state) {
            is ProfileScreenState.Idle -> {
                viewModel.loadData()
            }

            is ProfileScreenState.Loaded -> {
                ProfileScreen(
                    firstName = state.firstName,
                    secondName = state.secondName,
                    phone = state.phone,
                    email = state.email,
                    birthDay = state.birthDay?.time,
                    isMale = state.isMale,
                    city = state.city,
                    onSaveClick = viewModel::updateData,
                    onLogOutClick = viewModel::logOut
                )
            }
        }
    }
    Error(errorState = viewModel.errorState)
}

