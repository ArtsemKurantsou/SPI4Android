package com.kurantsov.pushservice

fun interface OnPushTokenChangedListener {
    fun onPushTokenChanged(pushToken: PushToken.Initialized)
}
