package com.kurantsov.pushservice.amazon

import android.content.Intent
import com.amazon.device.messaging.ADMMessageHandlerBase

class AmazonMessageLegacyHandler : ADMMessageHandlerBase(null) {
    override fun onMessage(intent: Intent?) {
        handleMessage(intent)
    }

    override fun onRegistrationError(error: String?) {
        handleRegistrationFailed(error ?: "Unknown reason")
    }

    override fun onRegistered(token: String?) {
        if (token != null) {
            handleRegistrationSuccess(token)
        } else {
            handleRegistrationFailed("Push token is null")
        }
    }

    override fun onUnregistered(message: String?) {
        handleUnregistered(message ?: "Unknown reason")
    }
}
