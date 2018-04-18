package com.geoschnitzel.treasurehunt.rest

import java.util.*

data class Message(val message: String, val timestamp: Date) {
    val messageUpperCase get() = message.toUpperCase()
}