package com.geoschnitzel.treasurehunt.utils.Webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.module.kotlin.KotlinModule;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class WebserviceAsyncTask<T> extends AsyncTask<RequestParams<T>, Void, T> {

    public static String Tag = "WebserviceAsyncTask";
    private WebServiceCallback<T> callback = null;

    public WebserviceAsyncTask(WebServiceCallback<T> callback) {
        this.callback = callback;
    }

    @SafeVarargs
    @Override
    public final T doInBackground(RequestParams<T>... requestParams) {
        RequestParams<T> params = requestParams[0];
        Log.d(Tag,params.toString());
        try {
            RestTemplate restTemplate = new MyRestTemplate(2*1000);
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.getObjectMapper().registerModule(new KotlinModule());
            restTemplate.getMessageConverters().add(converter);
            switch (params.method) {
                case GET:
                    return params.params == null ?
                            restTemplate.getForObject(params.url, params.returnType) :
                            restTemplate.getForObject(params.url, params.returnType, params.params);
                case DELETE:
                    if (params.params == null)
                        restTemplate.delete(params.url);
                    else
                        restTemplate.delete(params.url, params.params);
                    return null;
                case POST:

                    return params.params == null ?
                            restTemplate.postForObject(params.url, params.postObject, params.returnType) :
                            restTemplate.postForObject(params.url, params.postObject, params.returnType, params.params);
                case PUT:
                    if (params.params == null)
                        restTemplate.put(params.url, params.postObject);
                    else
                        restTemplate.put(params.url, params.postObject, params.params);
                    return null;
                default:
                    return null;

            }
        }
        catch(Exception ex)
        {
            return (T)null;
        }
    }
    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        this.callback.onResult(result);
    }
    class MyRestTemplate extends RestTemplate {
        MyRestTemplate(int timeout) {
            if (getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
                ((SimpleClientHttpRequestFactory) getRequestFactory()).setConnectTimeout(timeout);
                ((SimpleClientHttpRequestFactory) getRequestFactory()).setReadTimeout(timeout);
            } else if (getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {
                ((HttpComponentsClientHttpRequestFactory) getRequestFactory()).setReadTimeout(timeout);
                ((HttpComponentsClientHttpRequestFactory) getRequestFactory()).setConnectTimeout(timeout);
            }
        }
    }
}