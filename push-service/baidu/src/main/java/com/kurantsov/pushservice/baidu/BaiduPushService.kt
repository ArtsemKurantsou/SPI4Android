package com.kurantsov.pushservice.baidu

import android.content.Context
import com.baidu.android.pushservice.PushConstants
import com.baidu.android.pushservice.PushManager
import com.kurantsov.pushservice.PushService
import com.kurantsov.pushservice.PushServicePriority
import com.kurantsov.pushservice.PushServiceType

class BaiduPushService : PushService {
    override val type: PushServiceType = BaiduPushServiceType
    override val priority: PushServicePriority = PushServicePriority.Low

    override fun isAvailable(context: Context): Boolean  = true

    override fun initialize(context: Context) {
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, API_KEY)
    }

    private companion object {
        const val API_KEY = ""
    }
}
