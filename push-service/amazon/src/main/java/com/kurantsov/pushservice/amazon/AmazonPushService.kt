package com.kurantsov.pushservice.amazon

import android.content.Context
import com.amazon.device.messaging.ADM
import com.kurantsov.pushservice.PushService
import com.kurantsov.pushservice.PushServicePriority
import com.kurantsov.pushservice.PushServiceType

/**
 * Amazon device messaging implementation of the push service
 */
class AmazonPushService : PushService {
    override val type: PushServiceType = AmazonPushServiceType
    override val priority: PushServicePriority = PushServicePriority.High

    override fun isAvailable(context: Context): Boolean {
        return isAmazonServicesAvailable
    }

    override fun initialize(context: Context) {
        val adm = ADM(context)
        adm.registrationId?.let { token ->
            handleRegistrationSuccess(token)
        } ?: run {
            adm.startRegister()
        }
    }
}
