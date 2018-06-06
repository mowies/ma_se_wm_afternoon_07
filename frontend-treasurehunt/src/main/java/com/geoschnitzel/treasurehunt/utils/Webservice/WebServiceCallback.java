package com.geoschnitzel.treasurehunt.utils.Webservice;

public interface WebServiceCallback<T> {
    void onResult(T result);
}
