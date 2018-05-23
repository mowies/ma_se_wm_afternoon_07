package com.geoschnitzel.treasurehunt.model;

import org.springframework.http.HttpMethod;

import java.util.Map;

public class RequestParams<T> {
    String url;
    Map<String, ?> params;
    Class<T> returnType;
    Object postObject;
    HttpMethod method;

    public RequestParams(Class<T> returnType, String url, HttpMethod method, Object postObject, Map<String, ?> urlParams) {
        this.url = url;
        this.returnType = returnType;
        this.method = method;
        this.params = urlParams;
        this.postObject = postObject;
    }
}

