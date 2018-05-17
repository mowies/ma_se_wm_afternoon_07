package com.geoschnitzel.treasurehunt.rest

import java.text.MessageFormat
import java.util.*

data class Message(val message: String, val timestamp: Date) {
    val messageUpperCase get() = message.toUpperCase()
}

data class SearchParamItem(val filterText: String)
data class SHPurchaseItem(val shValue: Int,
                          var Price: Float,
                          var CurrencyID: Int,
                          var CurrencySymbol: String,
                          var CurrencyFormat: String,
                          var Title: String) {

    val priceAsText: String
        get() = MessageFormat.format(CurrencyFormat, Price, CurrencySymbol)

    val shValueAsText: String
        get() = String.format("%d", shValue)

    constructor(item: SHPurchaseItem) :
            this(item.shValue, item.Price, item.CurrencyID, item.CurrencySymbol, item.CurrencyFormat, item.Title) {
    }
}

data class SHListItem(val huntID: Long,
                      var Name: String,
                      var Author: String,
                      var Length: Float,
                      var Rating: Float,
                      var Description: String,
                      var Visited: Boolean) {
}

enum class HintType(val type: String) {
    IMAGE("IMAGE"),
    TEXT("TEXT"),
    COORDINATE("COORDINATE"),
    DIRECTION("DIRECTION")
}

data class Coordinate(val longitude: Double,
                      val latitude: Double) {
    constructor(item: Coordinate) :
            this(item.longitude, item.latitude) {
    }
}

data class HintItem(val id: Long,
                    val type: HintType,
                    val shValue: Int,
                    val timeToUnlockHint: Int,
                    val unlocked: Boolean,
                    val description: String?,
                    val url: String?,
                    val coordinate: Coordinate?,
                    val angle: Double?) {
}

data class GameTargetItem(
        val id: Long,
        val startTime: Date,
        var hints: List<HintItem>)

data class GameItem(
        val id: Long,
        val currentTarget: GameTargetItem)

data class UserItem(
        val id: Long)