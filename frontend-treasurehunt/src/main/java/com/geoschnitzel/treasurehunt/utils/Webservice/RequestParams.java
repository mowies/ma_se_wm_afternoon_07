package com.geoschnitzel.treasurehunt.utils.Webservice;

import android.util.ArrayMap;

import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RequestParams<T> {
    String url;
    Class<T> returnType;
    HttpMethod method;
    T postObject;
    Map<String, Object> params;

    public RequestParams(Class<T> returnType, String url, HttpMethod method) {
        this.url = url;
        this.returnType = returnType;
        this.method = method;
        this.postObject = null;
        this.params = null;
    }

    public String toString() {
        return method.toString() + " " + url + " " + params + " " + postObject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class<T> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<T> returnType) {
        this.returnType = returnType;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Object getPostObject() {
        return postObject;
    }

    public void setPostObject(T postObject) {
        this.postObject = postObject;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void addParam(String key,Object value) {
        if(params == null)
            params = new HashMap<>();
        this.params.put(key,value);
    }
}

