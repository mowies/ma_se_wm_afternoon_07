package com.geoschnitzel.treasurehunt.rest

import sun.security.krb5.internal.crypto.Des
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

data class SHListItem(var Name: String,
                      var Author: String,
                      var Length: Float,
                      var Rating: Float,
                      var Description: String,
                      var Visited: Boolean) {

    constructor(item: SHListItem) :
            this(item.Name, item.Author, item.Length, item.Rating, item.Description, item.Visited) {
    }
}
