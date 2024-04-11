package com.rammdakk.recharge.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences
import com.rammdakk.recharge.base.data.sp.EncryptedSharedPreferences.Companion.ACCESS_KEY
import com.rammdakk.recharge.base.theme.ReChargeTheme
import com.rammdakk.recharge.feature.auth.view.AuthActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerObservers()
        setContent {
            ReChargeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppContentHandler()
                }
            }
        }
    }

    private fun registerObservers() {
        EncryptedSharedPreferences(applicationContext).sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == ACCESS_KEY) {
                if (sharedPreferences.getString(key, null) == null) {
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
