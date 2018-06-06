package com.geoschnitzel.treasurehunt.rest

import com.fasterxml.jackson.annotation.JsonIgnore
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

data class CoordinateItem(val longitude: Double,
                          val latitude: Double)

data class HintItem(val id: Long,
                    val type: HintType,
                    val shvalue: Int,
                    val timetounlockhint: Int,
                    val unlocked: Boolean,
                    val description: String?,
                    val url: String?,
                    val coordinate: CoordinateItem?,
                    val angle: Double?) {
}

data class GameTargetItem(
        val id: Long,
        val starttime: Date,
        var hints: List<HintItem>) {

    @JsonIgnore
    fun getUnlockedHints():List<HintItem>{
        return this.hints.filter{ it.unlocked }
    }
    @JsonIgnore
    fun getLockedHints():List<HintItem>{
        return this.hints.filter { !it.unlocked }
    }
}

data class GameItem(
        val id: Long,
        val userid:Long,
        val huntid:Long,
        val targets: List<GameTargetItem>,
        val started: Date,
        val paused: Date?,
        val ended: Date?) {
    @JsonIgnore
    fun getCurrenttarget():GameTargetItem{
        return this.targets.get(this.targets.size - 1);
    }
}

data class UserItem(
        val id: Long,
        val balance:Int)

enum class TransactionType(val type: String) {
    Purchase("PURCHASE"),
    Earned("EARNED"),
    Used("USED")
};