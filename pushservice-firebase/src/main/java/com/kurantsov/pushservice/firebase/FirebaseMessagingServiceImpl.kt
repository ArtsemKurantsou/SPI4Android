package com.kurantsov.pushservice.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kurantsov.pushservice.PushServiceManager

class FirebaseMessagingServiceImpl : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        PushServiceManager.processMessage(message.data, message.from ?: "UNKNOWN")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        PushServiceManager.setPushToken(token, FirebasePushServiceType)
    }
}
