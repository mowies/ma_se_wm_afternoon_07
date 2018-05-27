package com.geoschnitzel.treasurehunt.model;

import com.geoschnitzel.treasurehunt.BuildConfig;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.Message;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;
import com.geoschnitzel.treasurehunt.utils.Webservice.RequestParams;
import com.geoschnitzel.treasurehunt.utils.Webservice.WebServiceCallback;
import com.geoschnitzel.treasurehunt.utils.Webservice.WebserviceAsyncTask;

import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

public class WebService {
    private static UserItem user = null;
    private static WebService instance = null;

    public static WebService instance() {
        if (instance == null)
            instance = new WebService();
        return instance;
    }

    private WebService(){
        login(result -> user = result);
    }





    //In the RequestFunctions we can define the whole Api Call
    public static class RequestFunctions
    {
        static String EndPoint = BuildConfig.ENDPOINT;
        static RequestParams<UserItem> Login = new RequestParams<>(UserItem.class,EndPoint + "/api/user/login",HttpMethod.GET);
        static RequestParams<UserItem>        GetUser = new RequestParams<>(UserItem.class,EndPoint + "/api/user/{userID}",HttpMethod.GET);
        static RequestParams<Message>        HelloWorld = new RequestParams<>(Message.class,EndPoint + "/api/helloWorld",HttpMethod.GET);
        static RequestParams<SHListItem[]>      GetSHList = new RequestParams<>(SHListItem[].class,EndPoint + "/api/hunt/",HttpMethod.GET);
        //static RequestParams<SHListItem>      GetSHItem = new RequestParams<>(SHListItem.class,EndPoint + "/api/hunt/{huntID}",HttpMethod.GET);
        static RequestParams<GameItem>        StartGame = new RequestParams<>(GameItem.class,EndPoint + "/api/user/{userID}/game/startGame/{huntID}",HttpMethod.GET) ;
        static RequestParams<GameItem>        GetGame = new RequestParams<>(GameItem.class,EndPoint + "/api/user/{userID}/game/{gameID}",HttpMethod.GET) ;
        static RequestParams<Boolean>        BuyHint = new RequestParams<>(Boolean.class,EndPoint + "/api/user/{userID}/game/{gameID}/buyHint/{hintID}",HttpMethod.GET) ;
        static RequestParams<Boolean>        UnlockHint = new RequestParams<>(Boolean.class,EndPoint + "/api/user/{userID}/game/{gameID}/unlockHint/{hintID}",HttpMethod.GET) ;
    }



    //----------------------------------------------------------------------------------------------
    //Webservice functions for Synchronos and Asynchronos Call
    //----------------------------------------------------------------------------------------------

    //region User
    private void login()
    {
        RequestParams params = RequestFunctions.Login;
        user = new WebserviceAsyncTask<UserItem>(null).doInBackground(params);
    }
    private void login(WebServiceCallback<UserItem> callback)
    {
        RequestParams params = RequestFunctions.Login;
        new WebserviceAsyncTask<>(callback).execute(params);
    }

    public UserItem getUser()
    {
        RequestParams params = RequestFunctions.GetUser;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
        }});
        return new WebserviceAsyncTask<UserItem>(null).doInBackground(params);
    }
    public void getUser(WebServiceCallback<UserItem> callback)
    {
        RequestParams params = RequestFunctions.GetUser;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
        }});
        new WebserviceAsyncTask<>(callback).execute(params);
    }
    //endregion

    //region Game


    public void buyHint(WebServiceCallback<Boolean> callback,long gameID,long hintID) {
        RequestParams params = RequestFunctions.BuyHint;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
            put("hintID",hintID);
        }});
        new WebserviceAsyncTask<>(callback).execute(params);
    }
    public Boolean buyHint(long hintID,long gameID) {
        RequestParams params = RequestFunctions.BuyHint;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
            put("hintID",hintID);
        }});
        return new WebserviceAsyncTask<Boolean>(null).doInBackground(params);
    }

    public void unlockHint(WebServiceCallback<Boolean> callback,long gameID,long hintID) {
        RequestParams params = RequestFunctions.UnlockHint;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
            put("hintID",hintID);
        }});
        new WebserviceAsyncTask<>(callback).execute(params);
    }
    public Boolean unlockHint(long hintID,long gameID) {
        RequestParams params = RequestFunctions.UnlockHint;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
            put("hintID",hintID);
        }});
        return new WebserviceAsyncTask<Boolean>(null).doInBackground(params);
    }

    public void getGame(WebServiceCallback<GameItem> callback,long gameID) {
        RequestParams params = RequestFunctions.GetGame;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
        }});
        new WebserviceAsyncTask<>(callback).execute(params);
    }
    public GameItem getGame(long gameID) {
        RequestParams params = RequestFunctions.GetGame;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("gameID",gameID);
        }});
        return new WebserviceAsyncTask<GameItem>(null).doInBackground(params);
    }


    public void startGame(WebServiceCallback<GameItem> callback,Long huntID) {
        RequestParams params = RequestFunctions.StartGame;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("huntID",huntID);
        }});
        new WebserviceAsyncTask<>(callback).execute(params);
    }
    public GameItem startGame(long huntID) {
        RequestParams params = RequestFunctions.StartGame;
        params.setParams(new HashMap<String,Long>()
        {{
            put("userID",user.getId());
            put("huntID",huntID);
        }});
        return new WebserviceAsyncTask<GameItem>(null).doInBackground(params);
    }
    //endregion

    //region Test
    public Message getHelloWorldMessage() {
        return new WebserviceAsyncTask<Message>(null).doInBackground(RequestFunctions.HelloWorld);
    }

    public void getHelloWorldMessage(WebServiceCallback<Message> callback) {
        RequestParams params = RequestFunctions.HelloWorld;
        new WebserviceAsyncTask<>(callback).execute(params);
    }

    //endregion

    //region Hunt
    public List<SHListItem> getSHListItems(){
        SHListItem[] result = new WebserviceAsyncTask<SHListItem[]>(null).doInBackground(RequestFunctions.GetSHList);
        if(result == null)
            return null;
        return asList(result);
    }

    public void getSHListItems(WebServiceCallback<SHListItem[]> callback) {
        RequestParams params = RequestFunctions.GetSHList;
        new WebserviceAsyncTask<>(callback).execute(params);
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





}
