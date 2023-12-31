package com.kurantsov.pushservice

fun interface OnPushMessageReceivedListener {
    fun onPushMessageReceived(message: Map<String, String>, sender: String)
}
