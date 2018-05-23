package com.geoschnitzel.treasurehunt.rest

import com.fasterxml.jackson.annotation.JsonIgnore
import java.text.MessageFormat
import java.util.*

data class Message(val message: String, val timestamp: Date) {

    @get:JsonIgnore
    val messageUpperCase get() = message.toUpperCase()
}

data class SearchParamItem(val filterText: String)
data class SHPurchaseItem(val shvalue: Int,
                          var price: Float,
                          var currencyid: Int,
                          var currencysymbol: String,
                          var currencyformat: String,
                          var title: String) {

    val priceAsText: String
        get() = MessageFormat.format(currencyformat, price, currencysymbol)

    val shValueAsText: String
        get() = String.format("%d", shvalue)

    constructor(item: SHPurchaseItem) :
            this(item.shvalue, item.price, item.currencyid, item.currencysymbol, item.currencyformat, item.title) {
    }
}

data class SHListItem(var name: String,
                      var author: String,
                      var length: Float,
                      var rating: Float,
                      var description: String,
                      var visited: Boolean)
