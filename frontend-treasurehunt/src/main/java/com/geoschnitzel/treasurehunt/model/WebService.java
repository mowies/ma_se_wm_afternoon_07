package com.geoschnitzel.treasurehunt.model;

import android.location.Location;

import com.geoschnitzel.treasurehunt.BuildConfig;
import com.geoschnitzel.treasurehunt.rest.CoordinateItem;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.Message;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;
import com.geoschnitzel.treasurehunt.utils.Webservice.RequestParams;
import com.geoschnitzel.treasurehunt.utils.Webservice.WebServiceCallback;
import com.geoschnitzel.treasurehunt.utils.Webservice.WebserviceAsyncTask;
import com.google.common.util.concurrent.Futures;

import org.springframework.http.HttpMethod;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;

public class WebService {
    private Future<UserItem> user = null;
    private static WebService instance = null;
    private AtomicLong timeDiffSC;

    public static WebService instance() {
        if (instance == null) {
            instance = new WebService();
        }
        return instance;
    }

    private WebService() {
        timeDiffSC = new AtomicLong(0);
    }



    //In the RequestFunctions we can define the whole Api Call
    public static class RequestFunctions {
        final static String EndPoint = BuildConfig.ENDPOINT;
        final static RequestParams<UserItem> Login = new RequestParams<>(UserItem.class, EndPoint + "/api/user/login", HttpMethod.GET);
        final static RequestParams<CoordinateItem> SendUserLocation = new RequestParams<>(CoordinateItem.class, EndPoint + "/api/user/{userID}/game/{gameID}/location/", HttpMethod.POST);
        final static RequestParams<UserItem> GetUser = new RequestParams<>(UserItem.class, EndPoint + "/api/user/{userID}", HttpMethod.GET);
        final static RequestParams<Message> HelloWorld = new RequestParams<>(Message.class, EndPoint + "/api/helloWorld", HttpMethod.GET);
        final static RequestParams<SHListItem[]> GetSHList = new RequestParams<>(SHListItem[].class, EndPoint + "/api/hunt/", HttpMethod.GET);
        //final static RequestParams<SHListItem> GetSHItem = new RequestParams<>(SHListItem.class,EndPoint + "/api/hunt/{huntID}",HttpMethod.GET);
        final static RequestParams<GameItem> StartGame = new RequestParams<>(GameItem.class, EndPoint + "/api/user/{userID}/game/startGame/{huntID}", HttpMethod.GET);
        final static RequestParams<Boolean> CheckReachedTarget = new RequestParams<>(Boolean.class, EndPoint + "/api/user/{userID}/game/{gameID}/reachedTarget/", HttpMethod.GET);
        final static RequestParams<GameItem> GetGame = new RequestParams<>(GameItem.class, EndPoint + "/api/user/{userID}/game/{gameID}", HttpMethod.GET);
        final static RequestParams<Boolean> BuyHint = new RequestParams<>(Boolean.class, EndPoint + "/api/user/{userID}/game/{gameID}/buyHint/{hintID}", HttpMethod.GET);
        final static RequestParams<Boolean> UnlockHint = new RequestParams<>(Boolean.class, EndPoint + "/api/user/{userID}/game/{gameID}/unlockHint/{hintID}", HttpMethod.GET);
        final static RequestParams<Long> GetCurrentTime = new RequestParams<>(Long.class, EndPoint + "/api/time/", HttpMethod.GET);
    }


    //----------------------------------------------------------------------------------------------
    //Webservice functions for Synchronous and Asynchronous Call
    //----------------------------------------------------------------------------------------------

    //region TimeSync
    public void syncTimeDifference() {
        RequestParams params = RequestFunctions.GetCurrentTime;
        final Long start = new Date().getTime();
        new WebserviceAsyncTask<Long>((result -> {
            Long end = new Date().getTime();
            timeDiffSC.set(result - start - ((end - start) / 2));
        })).execute(params);
    }

    public Long getTimeDifference() {
        return timeDiffSC.get();
    }

    //endregion
    //region User

    public void sendUserLocation(Location mLastKnownLocation,long gameID) {
        RequestParams params = RequestFunctions.SendUserLocation;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("gameID", gameID);
        params.setPostObject(new CoordinateItem(mLastKnownLocation.getLongitude(),mLastKnownLocation.getLatitude()));
        new WebserviceAsyncTask<>(null).execute(params);
    }

    public void loginSync() {
        if (user != null && !user.isDone())
            return;

        RequestParams params = RequestFunctions.Login;
        user = Futures.immediateFuture(
                new WebserviceAsyncTask<UserItem>(null).doInBackground(params)
        );
    }

    public void loginAsync() {
        if (user != null && !user.isDone())
            return;

        RequestParams params = RequestFunctions.Login;

        WebserviceAsyncTask<UserItem> task = new WebserviceAsyncTask<>(null);
        user = task;
        task.execute(params);
    }

    public UserItem getUser() {
        RequestParams params = RequestFunctions.GetUser;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        return new WebserviceAsyncTask<UserItem>(null).doInBackground(params);
    }

    public void getUser(WebServiceCallback<UserItem> callback) {
        RequestParams params = RequestFunctions.GetUser;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        new WebserviceAsyncTask<>(callback).execute(params);
    }
    //endregion

    //region Game

    public void checkReachedTarget(long gameID) {
        RequestParams params = RequestFunctions.CheckReachedTarget;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("gameID", gameID);
        new WebserviceAsyncTask<Boolean>(null).doInBackground(params);
    }

    public void checkReachedTarget(WebServiceCallback<Boolean> callback,long gameID) {
        RequestParams params = RequestFunctions.CheckReachedTarget;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("gameID", gameID);
        new WebserviceAsyncTask<Boolean>(callback).execute(params);
    }

    public void buyHint(WebServiceCallback<Boolean> callback, long gameID, long hintID) {
        RequestParams params = RequestFunctions.BuyHint;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("gameID", gameID);
        params.addParam("hintID", hintID);

        new WebserviceAsyncTask<Boolean>(callback).execute(params);
    }

    public Boolean buyHint(long hintID, long gameID) {
        RequestParams params = RequestFunctions.BuyHint;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("gameID", gameID);
        params.addParam("hintID", hintID);
        return new WebserviceAsyncTask<Boolean>(null).doInBackground(params);
    }

    public void unlockHint(WebServiceCallback<Boolean> callback, long gameID, long hintID) {
        RequestParams params = RequestFunctions.UnlockHint;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("gameID", gameID);
        params.addParam("hintID", hintID);
        new WebserviceAsyncTask<>(callback).execute(params);
    }

    public Boolean unlockHint(long hintID, long gameID) {
        RequestParams params = RequestFunctions.UnlockHint;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("gameID", gameID);
        params.addParam("hintID", hintID);
        return new WebserviceAsyncTask<Boolean>(null).doInBackground(params);
    }

    public void getGame(WebServiceCallback<GameItem> callback, long gameID) {
        RequestParams params = RequestFunctions.GetGame;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("gameID", gameID);
        new WebserviceAsyncTask<>(callback).execute(params);
    }

    public GameItem getGame(long gameID) {
        RequestParams params = RequestFunctions.GetGame;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("gameID", gameID);
        return new WebserviceAsyncTask<GameItem>(null).doInBackground(params);
    }


    public void startGame(WebServiceCallback<GameItem> callback, Long huntID) {
        RequestParams params = RequestFunctions.StartGame;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("huntID", huntID);

        new WebserviceAsyncTask<>(callback).execute(params);
    }

    public GameItem startGame(long huntID) {
        RequestParams params = RequestFunctions.StartGame;
        params.addParam("userID", Futures.lazyTransform(user, UserItem::getId));
        params.addParam("huntID", huntID);

        return new WebserviceAsyncTask<GameItem>(null).doInBackground(params);
    }

    //region Hunt
    public List<SHListItem> getSHListItems() {
        RequestParams params = RequestFunctions.GetSHList;
        SHListItem[] result = new WebserviceAsyncTask<SHListItem[]>(null).doInBackground(params);
        if (result == null)
            return null;
        return asList(result);
    }

    public void getSHListItems(WebServiceCallback<SHListItem[]> callback) {
        RequestParams params = RequestFunctions.GetSHList;
        new WebserviceAsyncTask<>(callback).execute(params);
    }
    //endregion

    public void getSHPurchaseItems(WebServiceCallback<List<SHPurchaseItem>> callback) {

        callback.onResult(
                asList(new SHPurchaseItem(10, 2.99f, 1, "$", "{0} {1}", "Bronze"),
                        new SHPurchaseItem(20, 4.99f, 1, "$", "{0} {1}", "Silber"),
                        new SHPurchaseItem(100, 7.99f, 1, "$", "{0} {1}", "Gold"),
                        new SHPurchaseItem(500, 12.99f, 1, "$", "{0} {1}", "Platin"),
                        new SHPurchaseItem(1000, 17.99f, 1, "$", "{0} {1}", "Rodium"),
                        new SHPurchaseItem(5000, 24.99f, 1, "$", "{0} {1}", "Plutonium")));
    }


}
