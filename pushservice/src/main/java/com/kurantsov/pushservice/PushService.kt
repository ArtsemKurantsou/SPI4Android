package com.kurantsov.pushservice

import android.content.Context

/**
 * Interface used to provide push service implementation via SPI
 */
interface PushService {
    /**
     * Type of the push service implementation
     */
    val type: PushServiceType

    /**
     * Priority of the push service implementation
     */
    val priority: PushServicePriority

    /**
     * Returns if the push service implementation is available on the device
     */
    fun isAvailable(context: Context): Boolean

    /**
     * Initializes push service
     */
    fun initialize(context: Context)
}
