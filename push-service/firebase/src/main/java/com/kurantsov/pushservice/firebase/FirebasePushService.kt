package com.kurantsov.pushservice.firebase

import android.content.Context
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.kurantsov.pushservice.PushService
import com.kurantsov.pushservice.PushServiceManager
import com.kurantsov.pushservice.PushServicePriority
import com.kurantsov.pushservice.PushServiceType

class FirebasePushService : PushService {
    override val type: PushServiceType = FirebasePushServiceType
    override val priority: PushServicePriority = PushServicePriority.High

    override fun isAvailable(context: Context): Boolean {
        val availability =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        return availability == ConnectionResult.SUCCESS
    }

    override fun initialize(context: Context) {
        Firebase.messaging.token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            }

            val token = task.result

            PushServiceManager.setPushToken(token, FirebasePushServiceType)
        }
    }

    private companion object {
        const val TAG = "FirebasePushService"
    }
}
