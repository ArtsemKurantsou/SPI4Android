package com.kurantsov.pushservice.firebase

import com.kurantsov.pushservice.PushServiceType

object FirebasePushServiceType : PushServiceType {
    override val name: String = "FCM"
    override val description: String = "Firebase"
}
