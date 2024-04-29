package com.rammdakk.recharge.feature.exercises.activityList.view.components

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.rammdakk.recharge.R
import com.rammdakk.recharge.base.theme.ReChargeTokens
import com.rammdakk.recharge.base.theme.TextPrimaryLarge
import com.rammdakk.recharge.base.theme.TextPrimarySmall
import com.rammdakk.recharge.base.theme.getThemedColor
import com.rammdakk.recharge.feature.exercises.activityList.view.model.SortingTypes

@Composable
fun SortBottomSheet(sortingType: SortingTypes, onSort: (SortingTypes) -> Unit) {
    val currentState = remember {
        mutableStateOf(sortingType)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
    ) {
        TextPrimaryLarge(
            text = stringResource(id = R.string.exercise_filters),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        SortingTypes.entries.forEach {
            RadioButton(sortingType = it, currentState = currentState)
        }
        TextPrimaryLarge(
            text = stringResource(id = R.string.exercise_apply),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(50))
                .background(ReChargeTokens.BackgroundColored.getThemedColor())
                .clickable { onSort.invoke(currentState.value) }
                .padding(vertical = 12.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RadioButton(sortingType: SortingTypes, currentState: MutableState<SortingTypes>) {
    val geoPermission =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION) { isGranted ->
            if (isGranted) {
                currentState.value = SortingTypes.LOCATION
            } else {
                currentState.value = SortingTypes.TIME
            }
        }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                if (sortingType != SortingTypes.LOCATION || geoPermission.status.isGranted) {
                    currentState.value = sortingType
                } else {
                    geoPermission.launchPermissionRequest()
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = currentState.value == sortingType,
            colors = RadioButtonDefaults.colors()
                .copy(selectedColor = ReChargeTokens.BackgroundColored.getThemedColor()),
            onClick = {
                if (sortingType != SortingTypes.LOCATION || geoPermission.status.isGranted) {
                    currentState.value = sortingType
                } else {
                    geoPermission.launchPermissionRequest()
                }
            })
        TextPrimarySmall(text = stringResource(id = sortingType.res))
    }

}


@Preview
@Composable
fun SortBottomSheetPreview() {
    SortBottomSheet(sortingType = SortingTypes.TIME) {}
}