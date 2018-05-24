package com.geoschnitzel.treasurehunt.rest

import com.fasterxml.jackson.annotation.JsonIgnore
import java.text.MessageFormat

data class SearchParamItem(val filterText: String)
data class SHPurchaseItem(val shValue: Int,
                          var price: Float,
                          var currencyID: Int,
                          var currencySymbol: String,
                          var currencyFormat: String,
                          var title: String) {

    @get:JsonIgnore
    val priceAsText: String
        get() = MessageFormat.format(currencyFormat, price, currencySymbol)

    @get:JsonIgnore
    val shValueAsText: String
        get() = String.format("%d", shValue)

    constructor(item: SHPurchaseItem) :
            this(item.shValue, item.price, item.currencyID, item.currencySymbol, item.currencyFormat, item.title) {
    }
}

data class SHListItem(var name: String,
                      var author: String,
                      var length: Float,
                      var rating: Float,
                      var description: String,
                      var visited: Boolean) {

    constructor(item: SHListItem) :
            this(item.name, item.author, item.length, item.rating, item.description, item.visited) {
    }
}
