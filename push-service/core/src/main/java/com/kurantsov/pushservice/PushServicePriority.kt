package com.kurantsov.pushservice

sealed class PushServicePriority(val value: Int) {
    object High : PushServicePriority(0)
    object Medium : PushServicePriority(1)
    object Low : PushServicePriority(2)
}
