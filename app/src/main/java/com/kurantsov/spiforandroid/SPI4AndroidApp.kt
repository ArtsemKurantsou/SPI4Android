package com.kurantsov.spiforandroid

import android.app.Application
import com.kurantsov.pushservice.PushServiceManager
import com.kurantsov.pushservice.PushToken

class SPI4AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PushServiceManager.addOnPushTokenChangedListener(::uploadPushTokenToServer)
        PushServiceManager.addPushMessageListener(::handlePushMessage)
    }

    private fun uploadPushTokenToServer(pushToken: PushToken.Initialized) {
        // Make API request to BE to upload the token to the server
    }

    private fun handlePushMessage(message: Map<String, String>, sender: String) {
        // Process push message
    }
}
