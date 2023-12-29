package com.kurantsov.pushservice

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class PushServiceInitializer : Initializer<PushServiceManager> {
    override fun create(context: Context): PushServiceManager {
        runCatching {
            PushServiceManager.initialize(context)
        }.onFailure { e ->
            Log.e(TAG, "create: failed to initialize push service", e)
        }.onSuccess {
            Log.d(TAG, "create: Push service initialized successfully")
        }
        return PushServiceManager
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    private companion object {
        const val TAG = "PushServiceInitializer"
    }
}
