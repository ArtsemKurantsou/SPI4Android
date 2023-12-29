package com.kurantsov.pushservice.huawei

import android.os.Bundle
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import com.kurantsov.pushservice.PushServiceManager

class HmsMessageServiceImpl : HmsMessageService() {
    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        message?.let {
            PushServiceManager.processMessage(it.dataOfMap, it.from)
        }
    }

    override fun onNewToken(token: String?, bundle: Bundle) {
        super.onNewToken(token, bundle)
        token?.let {
            PushServiceManager.setPushToken(it, HuaweiPushServiceType)
        }
    }
}
