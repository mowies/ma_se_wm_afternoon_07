package com.geoschnitzel.treasurehunt.model;

import android.os.AsyncTask;

import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.geoschnitzel.treasurehunt.BuildConfig;
import com.geoschnitzel.treasurehunt.rest.Message;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;
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
    private WebService(){}

    public interface WebServiceCallback<T> {
        void onResult(T result);
    }

    //In the RequestFunctions we can define the whole Api Call
    public static class RequestFunctions
    {
        public static String EndPoint = BuildConfig.ENDPOINT;

        public static RequestParams<Message> HelloWorld = new RequestParams<>(Message.class, EndPoint + "/api/helloWorld", HttpMethod.GET, null, null);
        public static RequestParams<SHListItem[]> GetSHList = new RequestParams<>(SHListItem[].class, EndPoint + "/api/hunt/list", HttpMethod.GET, null, null);
        // public static RequestParams<GameItem>        StartGame = new RequestParams<>(GameItem.class,EndPoint + "/api/hunt/startGame/{0}",HttpMethod.GET,null,null) ;
    }



    //----------------------------------------------------------------------------------------------
    //Webservice functions for Synchronos and Asynchronos Call
    //----------------------------------------------------------------------------------------------

    public Message getHelloWorldMessage() {
        return new WebserviceAsyncTask<Message>(null).doInBackground(RequestFunctions.HelloWorld);
    }
    public void getHelloWorldMessage(WebServiceCallback<Message> callback) {
        new WebserviceAsyncTask<Message>(callback).execute(RequestFunctions.HelloWorld);
    }
    public List<SHListItem> getSHListItems(){

        return asList(new WebserviceAsyncTask<SHListItem[]>(null).doInBackground(RequestFunctions.GetSHList));
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

}
