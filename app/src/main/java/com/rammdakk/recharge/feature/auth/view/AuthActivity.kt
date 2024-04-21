package com.rammdakk.recharge.feature.auth.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.rammdakk.recharge.base.MainActivity
import com.rammdakk.recharge.base.theme.ReChargeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ReChargeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthContent(authViewModel)
                }
            }
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        authViewModel.isLoggedIn.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
        }

        authViewModel.openLink.observe(this) {
            if (it == null) return@observe
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            ContextCompat.startActivity(this, browserIntent, null)
        }
    }

}