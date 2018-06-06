package com.geoschnitzel.treasurehunt.utils.Webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.module.kotlin.KotlinModule;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.Future;

public class WebserviceAsyncTask<T> extends AsyncTask<RequestParams<T>, Void, T> implements Future<T> {

    public static final String TAG = "WebserviceAsyncTask";
    private WebServiceCallback<T> callback = null;

    public WebserviceAsyncTask(WebServiceCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public T doInBackground(RequestParams<T>... requestParams) {
        try {
            RequestParams<T> requestDefinition = requestParams[0];

            if(requestDefinition.getParams() != null) {
                for (String key : requestDefinition.getParams().keySet()) {
                    Object value = requestDefinition.getParams().get(key);
                    if (value instanceof Future) {
                        value = ((Future) value).get();
                    }
                    requestDefinition.getParams().put(key, value);
                }
            }
            Log.d(TAG, requestDefinition.toString());

            RestTemplate restTemplate = new MyRestTemplate(10 * 1000);
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.getObjectMapper().registerModule(new KotlinModule());
            restTemplate.getMessageConverters().add(converter);
            switch (requestDefinition.method) {
                case GET:
                    return requestDefinition.params == null ?
                            restTemplate.getForObject(requestDefinition.url, requestDefinition.returnType) :
                            restTemplate.getForObject(requestDefinition.url, requestDefinition.returnType, requestDefinition.params);
                case DELETE:
                    if (requestDefinition.params == null)
                        restTemplate.delete(requestDefinition.url);
                    else
                        restTemplate.delete(requestDefinition.url, requestDefinition.params);
                    return null;
                case POST:
                    HttpEntity<T> request = new HttpEntity<T>(requestDefinition.postObject);
                    return requestDefinition.params == null ?
                            restTemplate.postForObject(requestDefinition.url, request, requestDefinition.returnType) :
                            restTemplate.postForObject(requestDefinition.url, request, requestDefinition.returnType, requestDefinition.params);
                case PUT:
                    if (requestDefinition.params == null)
                        restTemplate.put(requestDefinition.url, requestDefinition.postObject);
                    else
                        restTemplate.put(requestDefinition.url, requestDefinition.postObject, requestDefinition.params);
                    return null;
                default:
                    return null;

            }
        } catch (Exception ex) {
            Log.e(TAG, "Webservice call failed", ex);
            return (T) null;
        }
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        if(this.callback != null)
            this.callback.onResult(result);
    }

    @Override
    public boolean isDone() {
        return super.getStatus() == Status.FINISHED;
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