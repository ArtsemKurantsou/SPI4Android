package com.kurantsov.pushservice.amazon

import android.content.Context
import android.content.Intent
import com.amazon.device.messaging.ADMMessageHandlerJobBase

class AmazonMessageHandler : ADMMessageHandlerJobBase() {
    override fun onMessage(context: Context?, intent: Intent?) {
        handleMessage(intent)
    }

    override fun onRegistrationError(context: Context?, error: String?) {
        handleRegistrationFailed(error ?: "Unknown reason")
    }

    override fun onRegistered(context: Context?, token: String?) {
        if (token != null) {
            handleRegistrationSuccess(token)
        } else {
            handleRegistrationFailed("Push token is null")
        }
    }

    override fun onUnregistered(context: Context?, message: String?) {
        handleUnregistered(message ?: "Unknown reason")
    }
}
