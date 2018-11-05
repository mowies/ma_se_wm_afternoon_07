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
                    var unlocked: Boolean,
                    val description: String?,
                    val url: String?,
                    val coordinate: CoordinateItem?,
                    val angle: Double?) {
}
data class AreaItem(
        val coordinate: CoordinateItem,
        val radius: Int)
data class GameTargetItem(
        val id: Long,
        val starttime: Date,
        var endtime: Date?,
        var hints: List<HintItem>,
        var area: AreaItem) {

    @JsonIgnore
    fun getUnlockedHints():List<HintItem>{
        return this.hints.filter{ it.unlocked }
    }
    @JsonIgnore
    fun getLockedHints():List<HintItem>{
        return this.hints.filter { !it.unlocked }
    }
}
data class TargetItem(
        val id: Long,
        var hints: List<HintItem>,
        var area: AreaItem) {
}

data class HuntItem(
        val id: Long,
        val targets: List<TargetItem>,
        val name:String,
        val description: String,
        val maxSpeed: Int,
        val creator: String,
        val startArea: AreaItem) {
}
data class GameItem(
        var targets: List<GameTargetItem>,
        var starttime: Date,
        var endtime:Date?){

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

data class CreateCoordinateItem(val name: String,
                                val coordinate: CoordinateItem) {
}
