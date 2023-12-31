package com.kurantsov.pushservice

import android.content.Context
import android.util.Log
import java.util.ServiceLoader
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicBoolean

object PushServiceManager {
    private const val TAG = "PushServiceManager"
    var pushToken: PushToken = PushToken.NotInitialized
        private set

    private val isInitialized: AtomicBoolean = AtomicBoolean(false)
    private val tokenChangedListeners: MutableSet<OnPushTokenChangedListener> =
        CopyOnWriteArraySet()
    private val pushMessageListeners: MutableSet<OnPushMessageReceivedListener> =
        CopyOnWriteArraySet()
    private var selectedPushServiceType: PushServiceType? = null

    fun initialize(context: Context) {
        if (isInitialized.get()) {
            Log.d(TAG, "Push service is initialized already")
            return
        }
        synchronized(this) {
            if (isInitialized.get()) {
                Log.d(TAG, "Push service is initialized already")
                return
            }
            performServiceInitialization(context)
        }
    }

    private fun performServiceInitialization(context: Context) {
        //Loading push service implementations
        val serviceLoader = ServiceLoader.load(PushService::class.java)
        val selectedImplementation = serviceLoader
            .sortedBy { pusService -> pusService.priority.value }
            .firstOrNull { pushService ->
                val isAvailable = pushService.isAvailable(context)
                Log.d(
                    TAG, "Checking push service - ${pushService.type.description}, " +
                            "available - $isAvailable"
                )
                isAvailable
            }
        if (selectedImplementation != null) {
            selectedImplementation.initialize(context)
            selectedPushServiceType = selectedImplementation.type
            isInitialized.set(true)
            Log.i(TAG, "Push service initialized with ${selectedImplementation.type.description}")
        } else {
            Log.e(TAG, "Push service implementation failed. No implementations found!")
            throw IllegalStateException("No push service implementations found!")
        }
    }

    /**
     * Adds listener for the push token updates. Called immediately if token is available
     * already.
     */
    fun addOnPushTokenChangedListener(listener: OnPushTokenChangedListener) {
        tokenChangedListeners.add(listener)
        val currentToken = pushToken
        if (currentToken is PushToken.Initialized) {
            listener.onPushTokenChanged(currentToken)
        }
    }

    /**
     * Removes listener for the push token updates.
     */
    fun removeOnPushTokenChangedListener(listener: OnPushTokenChangedListener) {
        tokenChangedListeners.remove(listener)
    }

    /**
     * Called by push service implementation to notify about push token change.
     */
    fun setPushToken(token: String, serviceType: PushServiceType) {
        if (selectedPushServiceType != serviceType) {
            Log.w(
                TAG, "setPushToken called from unexpected implementation. " +
                        "Selected implementation - ${selectedPushServiceType?.description}, " +
                        "Called by - ${serviceType.description}"
            )
            return
        }
        val initializedToken = PushToken.Initialized(token, serviceType)
        this.pushToken = initializedToken
        tokenChangedListeners.forEach { listener ->
            listener.onPushTokenChanged(initializedToken)
        }
    }

    /**
     * Called by push service implementation to notify about push message.
     */
    fun processMessage(message: Map<String, String>, sender: String) {
        Log.d(TAG, "processMessage: sender - $sender, message - $message")
        pushMessageListeners.forEach { listener ->
            listener.onPushMessageReceived(message, sender)
        }
    }

    fun addPushMessageListener(listener: OnPushMessageReceivedListener) {
        pushMessageListeners.add(listener)
    }

    fun removePushMessageListener(listener: OnPushMessageReceivedListener) {
        pushMessageListeners.remove(listener)
    }

}
