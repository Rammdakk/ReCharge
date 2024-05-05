package com.rammdakk.recharge.feature.push

import android.content.ClipData
import android.content.ClipboardManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.rammdakk.recharge.feature.push.domain.NotificationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class PushNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    @Inject
    lateinit var dispatchers: Dispatchers

    private var scope: CoroutineScope? = null

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("token", token)
        clipboard.setPrimaryClip(clip)
        scope?.cancel()
        scope = CoroutineScope(SupervisorJob() + dispatchers.IO)
        scope?.launch {
            notificationRepository.updateToken(token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope?.cancel()
    }
}