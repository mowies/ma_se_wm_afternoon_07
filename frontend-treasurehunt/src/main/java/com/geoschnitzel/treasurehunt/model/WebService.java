package com.geoschnitzel.treasurehunt.model;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.geoschnitzel.treasurehunt.BuildConfig;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.Message;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class WebService {
    private static UserItem user = null;
    private static WebService instance = null;

    public static WebService instance() {
        if (instance == null)
            instance = new WebService();
        return instance;
    }

    private WebService(){
        Login(new WebServiceCallback<UserItem>() {
            @Override
            public void onResult(UserItem result) {
                user = result;
            }
        });
    }



    public interface WebServiceCallback<T> {
        void onResult(T result);
    }

    //In the RequestFunctions we can define the whole Api Call
    public static class RequestFunctions
    {
        public static String EndPoint = BuildConfig.ENDPOINT;
        public static RequestParams<UserItem>        Login = new RequestParams<>(UserItem.class,EndPoint + "/api/user/login",HttpMethod.GET);
        public static RequestParams<Message>        HelloWorld = new RequestParams<>(Message.class,EndPoint + "/api/helloWorld",HttpMethod.GET);
        public static RequestParams<SHListItem[]>      GetSHList = new RequestParams<>(SHListItem[].class,EndPoint + "/api/hunt/",HttpMethod.GET);
        public static RequestParams<SHListItem>      GetSHItem = new RequestParams<>(SHListItem.class,EndPoint + "/api/hunt/{huntID}",HttpMethod.GET);
        public static RequestParams<GameItem>        StartGame = new RequestParams<>(GameItem.class,EndPoint + "/api/user/{userID}/game/startGame/{huntID}",HttpMethod.GET) ;
        public static RequestParams<GameItem>        GetGame = new RequestParams<>(GameItem.class,EndPoint + "/api/user/{userID}/game/{gameID}",HttpMethod.GET) ;
        public static RequestParams<Boolean>        BuyHint = new RequestParams<>(Boolean.class,EndPoint + "/api/user/{userID}/game/{gameID}/buyHint/{hintID}",HttpMethod.GET) ;
        public static RequestParams<Boolean>        UnlockHint = new RequestParams<>(Boolean.class,EndPoint + "/api/user/{userID}/game/{gameID}/unlockHint/{hintID}",HttpMethod.GET) ;
    }



    //----------------------------------------------------------------------------------------------
    //Webservice functions for Synchronos and Asynchronos Call
    //----------------------------------------------------------------------------------------------

    //region User
    private void Login()
    {
        user = new WebserviceAsyncTask<UserItem>(null).doInBackground(RequestFunctions.Login);
    }
    private void Login(WebServiceCallback<UserItem> callback)
    {
        new WebserviceAsyncTask<UserItem>(callback).execute(RequestFunctions.Login);
    }
    //endregion

    //region Game


    public void buyHint(WebServiceCallback<Boolean> callback,long gameID,long hintID) {
        RequestParams params = RequestFunctions.BuyHint;
        params.params = new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
            put("hintID",hintID);
        }};
        new WebserviceAsyncTask<Boolean>(callback).execute(params);
    }
    public Boolean buyHint(long hintID,long gameID) {
        RequestParams params = RequestFunctions.BuyHint;
        params.params = new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
            put("hintID",hintID);
        }};
        return new WebserviceAsyncTask<Boolean>(null).doInBackground(params);
    }

    public void unlockHint(WebServiceCallback<Boolean> callback,long gameID,long hintID) {
        RequestParams params = RequestFunctions.UnlockHint;
        params.params = new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
            put("hintID",hintID);
        }};
        new WebserviceAsyncTask<Boolean>(callback).execute(params);
    }
    public Boolean unlockHint(long hintID,long gameID) {
        RequestParams params = RequestFunctions.UnlockHint;
        params.params = new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
            put("hintID",hintID);
        }};
        return new WebserviceAsyncTask<Boolean>(null).doInBackground(params);
    }

    public void getGame(WebServiceCallback<GameItem> callback,long gameID) {
        RequestParams params = RequestFunctions.GetGame;
        params.params = new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
        }};
        new WebserviceAsyncTask<GameItem>(callback).execute(params);
    }
    public GameItem getGame(long gameID) {
        RequestParams params = RequestFunctions.GetGame;
        params.params = new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
        }};
        return new WebserviceAsyncTask<GameItem>(null).doInBackground(params);
    }


    public void startGame(WebServiceCallback<GameItem> callback,Long huntID) {
        RequestParams params = RequestFunctions.StartGame;
        params.params = new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("huntID",huntID);
        }};
        new WebserviceAsyncTask<GameItem>(callback).execute(params);
    }
    public GameItem startGame(long huntID) {
        RequestParams params = RequestFunctions.StartGame;
        params.params = new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("huntID",huntID);
        }};
        return new WebserviceAsyncTask<GameItem>(null).doInBackground(params);
    }
    //endregion

    //region Test
    public Message getHelloWorldMessage() {
        return new WebserviceAsyncTask<Message>(null).doInBackground(RequestFunctions.HelloWorld);
    }

    public void getHelloWorldMessage(WebServiceCallback<Message> callback) {
        new WebserviceAsyncTask<Message>(callback).execute(RequestFunctions.HelloWorld);
    }

    //endregion

    //region Hunt
    public List<SHListItem> getSHListItems(){

        return asList(new WebserviceAsyncTask<SHListItem[]>(null).doInBackground(RequestFunctions.GetSHList));
    }

    public void getSHListItems(WebServiceCallback<SHListItem[]> callback) {
        new WebserviceAsyncTask<SHListItem[]>(callback).execute(RequestFunctions.GetSHList);
    }

    //endregion

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
        public  String Tag = "WebserviceAsyncTask";
        private WebService.WebServiceCallback<T> callback = null;

        public WebserviceAsyncTask(WebServiceCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        protected T doInBackground(RequestParams<T>... requestParams) {
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
                System.out.println(ex.getMessage());
                if(params.returnType.isInstance(Array.class))
                    return (T)emptyList();
                else
                    return null;
            }
        }
        @Override
        protected void onPostExecute(T result) {
            super.onPostExecute(result);
            this.callback.onResult(result);
        }
        protected class MyRestTemplate extends RestTemplate {
            public MyRestTemplate(int timeout) {
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


}
