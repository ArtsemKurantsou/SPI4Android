package com.kurantsov.pushservice

/**
 * Describes state of the push token
 */
sealed class PushToken {
    /**
     * Push token is not initialized (available) yet.
     */
    object NotInitialized : PushToken()

    /**
     * Push token is available.
     */
    class Initialized(val value: String, type: PushServiceType) : PushToken()
}
