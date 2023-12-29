package com.kurantsov.pushservice.amazon

import android.content.Intent
import android.util.Log
import com.kurantsov.pushservice.PushServiceManager
import org.json.JSONObject

private const val TAG = "AmazonServicesUtils"

/**
 * Returns if amazon device messaging is available on the device
 */
val isAmazonServicesAvailable: Boolean by lazy {
    try {
        Class.forName("com.amazon.device.messaging.ADM")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

/**
 * Returns if latest version of amazon device messaging is available on the device
 */
val isAmazonServicesLatestAvailable: Boolean by lazy {
    try {
        Class.forName("com.amazon.device.messaging.ADMMessageHandlerJobBase")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

internal fun handleMessage(intent: Intent?) {
    intent?.getStringExtra("message")?.let { messageJson ->
        runCatching {
            val jsonObject = JSONObject(messageJson)
            val  message = jsonObject.keys()
                .asSequence()
                .associateWith { key ->
                    jsonObject.getString(key)
                }
            PushServiceManager.processMessage(message, "default")
        }.onFailure { e ->
            Log.e(TAG, "handleMessage: failed to parse message", e)
        }
    }
}

internal fun handleRegistrationSuccess(pushToken: String) {
    PushServiceManager.setPushToken(pushToken, AmazonPushServiceType)
}

internal fun handleRegistrationFailed(error: String) {
    Log.e(TAG, "onRegistrationFailed: $error")
}

internal fun handleUnregistered(message: String) {
    Log.e(TAG, "onUnregistered: $message")
}