package com.geoschnitzel.treasurehunt.rest

import com.fasterxml.jackson.annotation.JsonIgnore
import java.text.MessageFormat
import java.util.*

data class Message(val message: String, val timestamp: Date) {

    @get:JsonIgnore
    val messageUpperCase get() = message.toUpperCase()
}

enum class HintType(val type: String) {
    IMAGE("IMAGE"),
    TEXT("TEXT"),
    COORDINATE("COORDINATE"),
    DIRECTION("DIRECTION")
};

data class Coordinate(val longitude: Double,
                      val latitude: Double) {
    constructor(item: Coordinate) :
            this(item.longitude, item.latitude) {
    }
}

data class HintItem(val id: Long,
                    val type: HintType,
                    val shvalue: Int,
                    val timetounlockhint: Int,
                    val unlocked: Boolean,
                    val description: String?,
                    val url: String?,
                    val coordinate: Coordinate?,
                    val angle: Double?) {
}

data class GameTargetItem(
        val id: Long,
        val starttime: Date,
        var hints: List<HintItem>) {

}

data class GameItem(
        val id: Long,
        val currenttarget: GameTargetItem) {
}

data class UserItem(
        val id: Long)

enum class TransactionType(val type: String) {
    Purchase("PURCHASE"),
    Earned("EARNED"),
    Used("USED")
};