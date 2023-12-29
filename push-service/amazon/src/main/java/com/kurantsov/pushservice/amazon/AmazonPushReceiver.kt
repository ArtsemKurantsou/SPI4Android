package com.kurantsov.pushservice.amazon

import com.amazon.device.messaging.ADMMessageReceiver

class AmazonPushReceiver : ADMMessageReceiver(AmazonMessageLegacyHandler::class.java) {
    init {
        if (isAmazonServicesLatestAvailable) {
            registerJobServiceClass(AmazonMessageHandler::class.java, JOB_ID)
        }
    }

    private companion object {
        const val JOB_ID = 100_100
    }
}
