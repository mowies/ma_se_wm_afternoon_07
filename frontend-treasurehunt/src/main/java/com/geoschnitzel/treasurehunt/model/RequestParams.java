package com.geoschnitzel.treasurehunt.model;
import org.springframework.http.HttpMethod;
import java.util.Map;

public class RequestParams<T> {
    String url;
    Class<T> returnType;
    HttpMethod method;
    Object postObject;
    Map<String, ?> params;
    public  RequestParams( Class<T> returnType, String url, HttpMethod method)
    {
        this.url = url;
        this.returnType = returnType;
        this.method = method;
        this.postObject = null;
        this.params = null;
    }
    public String toString()
    {
        return method.toString() + " " + url + " " + params;
    }
}

