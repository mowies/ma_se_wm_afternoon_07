package com.geoschnitzel.treasurehunt.model;

import com.geoschnitzel.treasurehunt.rest.Message;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.List;

public interface WebService {
    void retrieveSHPurchaseItems(WebServiceCallback<List<SHPurchaseItem>> callback);

    void retrieveSHListItems(WebServiceCallback<List<SHListItem>> callback);

    void retrieveHelloWorldMessage(WebServiceCallback<Message> callback);

    interface WebServiceCallback<T> {
        void onResult(T result);
    }
}
