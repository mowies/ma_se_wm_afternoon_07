package com.geoschnitzel.treasurehunt.model;

import android.location.Location;

import com.geoschnitzel.treasurehunt.BuildConfig;
import com.geoschnitzel.treasurehunt.rest.CoordinateItem;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HuntItem;
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
    private static WebService instance = null;
    private AtomicLong timeDiffSC;

    public static WebService instance() {
        if (instance == null) {
            instance = new WebService();
        }
        return instance;
    }

    private WebService()
    {
        //if (BuildConfig.DEBUG) {
            GenerateTestData();
        //}
        timeDiffSC = new AtomicLong(0);
    }



    //In the RequestFunctions we can define the whole Api Call
    public static class RequestFunctions {
        final static String EndPoint = BuildConfig.ENDPOINT;
        final static RequestParams<SHListItem[]> GetSHList = new RequestParams<>(SHListItem[].class, EndPoint + "/api/hunt/", HttpMethod.GET);
        final static RequestParams<HuntItem> GetHunt = new RequestParams<>(HuntItem.class, EndPoint + "/api/hunt/{huntID}", HttpMethod.GET);
        final static RequestParams<Long> GetCurrentTime = new RequestParams<>(Long.class, EndPoint + "/api/time/", HttpMethod.GET);
        final static RequestParams<Void> GenerateTestData = new RequestParams<>(Void.class, EndPoint + "/api/test/generatetestdata", HttpMethod.GET);


    }


    //----------------------------------------------------------------------------------------------
    //Webservice functions for Synchronous and Asynchronous Call
    //----------------------------------------------------------------------------------------------

    //region Test


    public void GenerateTestData() {
        RequestParams params = RequestFunctions.GenerateTestData;
        final Long start = new Date().getTime();
        new WebserviceAsyncTask<Void>(null).doInBackground(params);
    }
    //endregion
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

    //region Hunt

    public void getHunt(WebServiceCallback<HuntItem> callback, long huntID) {
        RequestParams params = RequestFunctions.GetHunt;
        params.addParam("huntID", huntID);
        new WebserviceAsyncTask<>(callback).execute(params);
    }

    public HuntItem getHunt(long huntID) {
        RequestParams params = RequestFunctions.GetHunt;
        params.addParam("gameID", huntID);
        return new WebserviceAsyncTask<HuntItem>(null).doInBackground(params);
    }


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
