package com.kurantsov.pushservice.baidu

import android.content.Context
import com.baidu.android.pushservice.PushManager
import com.baidu.android.pushservice.PushMessageReceiver
import com.kurantsov.pushservice.PushServiceManager
import org.json.JSONObject


class BaiduPushMessageReceiver : PushMessageReceiver() {
    override fun onBind(
        context: Context?,
        errorCode: Int,
        appid: String,
        userId: String,
        channelId: String,
        requestId: String,
    ) {
        PushManager.requestOppoNotification(context)
    }

    override fun onUnbind(context: Context?, errorCode: Int, requestId: String) = Unit

    override fun onMessage(
        context: Context?,
        message: String,
        customContentString: String,
        notifyId: Int,
        source: Int,
    ) {
        runCatching {
            val messageJson = JSONObject(customContentString)
            val messageData = messageJson.keys()
                .asSequence()
                .associateBy { messageJson.getString(it) }
            PushServiceManager.processMessage(messageData, "$source")
        }
    }

    override fun onNotificationClicked(
        context: Context,
        title: String?,
        description: String?,
        customContentString: String,
    ) = Unit

    override fun onNotificationArrived(
        context: Context?,
        title: String?,
        description: String?,
        customContentString: String,
    ) = Unit

    override fun onSetTags(
        context: Context?,
        errorCode: Int,
        successTags: List<String?>,
        failTags: List<String?>,
        requestId: String,
    ) = Unit

    override fun onDelTags(
        context: Context?,
        errorCode: Int,
        successTags: List<String?>,
        failTags: List<String?>,
        requestId: String,
    ) = Unit

    override fun onListTags(
        context: Context?,
        errorCode: Int,
        tags: List<String?>,
        requestId: String,
    ) = Unit
}
