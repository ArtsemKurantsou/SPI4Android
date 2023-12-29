package com.kurantsov.pushservice.huawei

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import com.huawei.hms.push.HmsMessaging
import com.kurantsov.pushservice.PushService
import com.kurantsov.pushservice.PushServiceManager
import com.kurantsov.pushservice.PushServicePriority
import com.kurantsov.pushservice.PushServiceType
import kotlin.concurrent.thread

class HuaweiPushService : PushService {
    override val type: PushServiceType = HuaweiPushServiceType
    override val priority: PushServicePriority = PushServicePriority.Medium

    override fun isAvailable(context: Context): Boolean = true

    override fun initialize(context: Context) {
        thread(start = true) {
            try {
                HmsMessaging.getInstance(context).turnOnPush().result
                // Obtain the app ID from the agconnect-services.json file.
                val appId = "your APP_ID"

                // Set tokenScope to HCM.
                val tokenScope = "HCM"
                val token = HmsInstanceId.getInstance(context).getToken(appId, tokenScope)

                // Check whether the token is null.
                if (!TextUtils.isEmpty(token)) {
                    PushServiceManager.setPushToken(token, HuaweiPushServiceType)
                }
            } catch (e: ApiException) {
                Log.e(TAG, "get token failed, $e")
            }
        }
    }

    private companion object {
        const val TAG = "HuaweiPushService"
    }
}
