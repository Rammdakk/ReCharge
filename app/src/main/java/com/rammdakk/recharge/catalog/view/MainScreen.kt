package com.rammdakk.recharge.catalog.view

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rammdakk.recharge.base.MainViewModel

@Destination(start = true)
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    vm: MainViewModel =  hiltViewModel(LocalContext.current as ComponentActivity)
) {
    Log.d("Ramil", vm.toString())

    Column {
        Text(text = "Hi there, main screen")

        Spacer(modifier = Modifier.height(100.dp))

    }

}