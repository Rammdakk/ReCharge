package com.rammdakk.recharge.base.view.component.error

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rammdakk.recharge.base.theme.ReChargeTheme
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimarySmallInverseConstant
import com.rammdakk.recharge.base.theme.getThemedColor

@Composable
fun Error(errorState: State<ErrorState>) {
    AnimatedVisibility(
        visible = errorState.value !is ErrorState.Idle,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Crossfade(targetState = errorState.value, label = "") { state ->
            when (state) {
                is ErrorState.Idle -> {}
                is ErrorState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(10.dp)
                            .background(
                                ReChargeTokens.BackgroundError.getThemedColor(),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        TextPrimarySmallInverseConstant(
                            text = state.text,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                is ErrorState.Success -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(10.dp)
                            .background(
                                ReChargeTokens.BackgroundSuccess.getThemedColor(),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        TextPrimarySmallInverseConstant(
                            text = state.text,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }

    }
}


@Preview
@Composable
fun ErrorPreview() = ReChargeTheme {
    val error = remember {
        mutableStateOf<ErrorState>(ErrorState.Idle)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(
                Alignment.Center
            )
        ) {
            Button(
                onClick = {
                    error.value =
                        ErrorState.Error("Нет подключения к интернету Нет подключения к интернету Нет подключения к интернету Нет подключения к интернету")
                },

                ) {

            }
            Button(
                onClick = {
                    error.value =
                        ErrorState.Success("Нет подключения к интернету Нет подключения к интернету Нет подключения к интернету Нет подключения к интернету")
                },

                ) {

            }
            Button(
                onClick = { error.value = ErrorState.Idle },

                ) {

            }
        }


        Error(errorState = error)
    }

}