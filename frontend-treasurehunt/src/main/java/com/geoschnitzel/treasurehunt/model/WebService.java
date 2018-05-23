package com.geoschnitzel.treasurehunt.model;

import android.os.AsyncTask;

import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.geoschnitzel.treasurehunt.rest.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;

public class WebService {
    private static WebService instance = null;

    public static WebService instance() {
        if(instance == null)
            instance = new WebService();
        return instance;
    }
    private WebService()
    {
        generateTestData(result -> System.out.println("Test data generation complete!"));
    }

    public interface WebServiceCallback<T> {
        void onResult(T result);
    }

    //In the RequestFunctions we can define the whole Api Call
    public static class RequestFunctions
    {
        public static String EndPoint = "http://192.168.1.104:8080";

        public static RequestParams<Message>        HelloWorld = new RequestParams<>(Message.class,EndPoint + "/helloWorld",HttpMethod.GET,null,null);
        public static RequestParams<SHListItem[]>      GetSHList = new RequestParams<>(SHListItem[].class,EndPoint + "/api/hunt/getshlist",HttpMethod.GET,null,null);
        // public static RequestParams<GameItem>        StartGame = new RequestParams<>(GameItem.class,EndPoint + "/api/hunt/startGame/{0}",HttpMethod.GET,null,null) ;
        public static RequestParams<Void>        GenerateTestData = new RequestParams<>(Void.class,EndPoint + "/api/test/generateTestData",HttpMethod.GET,null,null);
    }



    //----------------------------------------------------------------------------------------------
    //Webservice functions for Synchronos and Asynchronos Call
    //----------------------------------------------------------------------------------------------

    private void generateTestData() {
        new WebserviceTask<Void>().execute(RequestFunctions.GenerateTestData);
    }
    private void generateTestData(WebServiceCallback<Void> callback) {
        new WebserviceAsyncTask<Void>(callback).execute(RequestFunctions.GenerateTestData);
    }
    public Message getHelloWorldMessage() {
        return new WebserviceTask<Message>().execute(RequestFunctions.HelloWorld);
    }
    public void getHelloWorldMessage(WebServiceCallback<Message> callback) {
        new WebserviceAsyncTask<Message>(callback).execute(RequestFunctions.HelloWorld);
    }
    public List<SHListItem> getSHListItems(){

        return asList(new WebserviceTask<SHListItem[]>().execute(RequestFunctions.GetSHList));
    }
    public void getSHListItems(WebServiceCallback<SHListItem[]> callback){
        new WebserviceAsyncTask<SHListItem[]>(callback).execute(RequestFunctions.GetSHList);
    }
    public void getSHPurchaseItems(WebServiceCallback<List<SHPurchaseItem>> callback){

        callback.onResult(
                asList(new SHPurchaseItem(10, 2.99f, 1, "$", "{0} {1}", "Bronze"),
                        new SHPurchaseItem(20, 4.99f, 1, "$", "{0} {1}", "Silber"),
                        new SHPurchaseItem(100, 7.99f, 1, "$", "{0} {1}", "Gold"),
                        new SHPurchaseItem(500, 12.99f, 1, "$", "{0} {1}", "Platin"),
                        new SHPurchaseItem(1000, 17.99f, 1, "$", "{0} {1}", "Rodium"),
                        new SHPurchaseItem(5000, 24.99f, 1, "$", "{0} {1}", "Plutonium")));
    }



    public class WebserviceAsyncTask<T> extends AsyncTask<RequestParams<T>, Void, T> {
        private WebService.WebServiceCallback<T> callback = null;

        public WebserviceAsyncTask(WebServiceCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        protected T doInBackground(RequestParams<T>... requestParams) {
            RequestParams<T> params = requestParams[0];
            RestTemplate restTemplate = new RestTemplate();
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

        @Override
        protected void onPostExecute(T result) {
            super.onPostExecute(result);
            this.callback.onResult(result);
        }
    }

    public class WebserviceTask<T> {
        protected T execute(RequestParams<T> params) {
            RestTemplate restTemplate = new RestTemplate();
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
    }
}
