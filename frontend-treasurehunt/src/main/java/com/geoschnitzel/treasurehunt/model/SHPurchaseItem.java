package com.geoschnitzel.treasurehunt.model;

import java.text.MessageFormat;

public class SHPurchaseItem {

    int SHValue;
    float Price;
    int CurrencyID;
    String CurrencySymbol;
    String CurrencyFormat;
    String Title;

    public SHPurchaseItem() {
    }

    public SHPurchaseItem(SHPurchaseItem item) {
        this.SHValue = item.SHValue;
        this.Price = item.Price;
        this.CurrencyID = item.CurrencyID;
        this.CurrencySymbol = item.CurrencySymbol;
        this.CurrencyFormat = item.CurrencyFormat;
        this.Title = item.Title;
    }

    public String getPriceAsText() {
        return MessageFormat.format(CurrencyFormat, Price, CurrencySymbol);
    }

    public int getSHValue() {
        return SHValue;
    }

    public SHPurchaseItem setSHValue(int SHValue) {
        this.SHValue = SHValue;
        return this;
    }

    public float getPrice() {
        return Price;
    }

    public SHPurchaseItem setPrice(float price) {
        Price = price;
        return this;
    }

    public int getCurrencyID() {
        return CurrencyID;
    }

    public SHPurchaseItem setCurrencyID(int currencyID) {
        CurrencyID = currencyID;
        return this;
    }

    public String getCurrencySymbol() {
        return CurrencySymbol;
    }

    public SHPurchaseItem setCurrencySymbol(String currencySymbol) {
        CurrencySymbol = currencySymbol;
        return this;
    }

    public String getCurrencyFormat() {
        return CurrencyFormat;
    }

    public SHPurchaseItem setCurrencyFormat(String currencyFormat) {
        CurrencyFormat = currencyFormat;
        return this;
    }

    public String getTitle() {
        return Title;
    }

    public SHPurchaseItem setTitle(String title) {
        Title = title;
        return this;
    }
}
