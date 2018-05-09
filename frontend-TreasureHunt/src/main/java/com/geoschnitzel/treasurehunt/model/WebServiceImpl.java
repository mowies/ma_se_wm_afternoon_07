package com.geoschnitzel.treasurehunt.model;

import android.os.AsyncTask;

import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.geoschnitzel.treasurehunt.rest.Message;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebServiceImpl implements WebService {
    private static WebServiceImpl instance = new WebServiceImpl();

    public static WebService instance() {
        return instance;
    }

    private static class RetrieveSHPurchaseItemsAsyncTask extends WebserviceAsyncTask<List<SHPurchaseItem>> {
        private RetrieveSHPurchaseItemsAsyncTask(WebServiceCallback<List<SHPurchaseItem>> callback) {
            super(callback);
        }

        @Override
        protected List<SHPurchaseItem> doInBackground(Void... voids) {
            List<SHPurchaseItem> testList = new ArrayList<SHPurchaseItem>();
            testList.add(new SHPurchaseItem(10, 2.99f, 1, "$", "{0} {1}", "Bronze"));
            testList.add(new SHPurchaseItem(20, 4.99f, 1, "$", "{0} {1}", "Silber"));
            testList.add(new SHPurchaseItem(100, 7.99f, 1, "$", "{0} {1}", "Gold"));
            testList.add(new SHPurchaseItem(500, 12.99f, 1, "$", "{0} {1}", "Platin"));
            testList.add(new SHPurchaseItem(1000, 17.99f, 1, "$", "{0} {1}", "Rodium"));
            testList.add(new SHPurchaseItem(5000, 24.99f, 1, "$", "{0} {1}", "Plutonium"));

            return testList;
        }
    }

    private static class RetrieveSHListItemsAsyncTask extends WebserviceAsyncTask<List<SHListItem>> {

        private RetrieveSHListItemsAsyncTask(WebServiceCallback<List<SHListItem>> callback) {
            super(callback);
        }

        @Override
        protected List<SHListItem> doInBackground(Void... voids) {
            String url = "http://10.0.2.2:8080/schnitzelHuntList";

            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.getObjectMapper().registerModule(new KotlinModule());
            restTemplate.getMessageConverters().add(converter);

            SHListItem[] requestResult = restTemplate.getForObject(url, SHListItem[].class);

            return Arrays.asList(requestResult);
        }
    }

    private static class RetrieveHelloWorldMessageAsyncTask extends WebserviceAsyncTask<Message> {
        private RetrieveHelloWorldMessageAsyncTask(WebServiceCallback<Message> callback) {
            super(callback);
        }

        @Override
        protected Message doInBackground(Void... voids) {
            String url = "http://10.0.2.2:8080/helloWorld";

            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.getObjectMapper().registerModule(new KotlinModule());
            restTemplate.getMessageConverters().add(converter);

            return restTemplate.getForObject(url, Message.class);
        }
    }

    @Override
    public void retrieveSHPurchaseItems(WebServiceCallback<List<SHPurchaseItem>> callback) {
        new RetrieveSHPurchaseItemsAsyncTask(callback).execute();
    }

    @Override
    public void retrieveSHListItems(WebServiceCallback<List<SHListItem>> callback) {
        new RetrieveSHListItemsAsyncTask(callback).execute();
    }

    @Override
    public void retrieveHelloWorldMessage(final WebServiceCallback<Message> callback) {
        new RetrieveHelloWorldMessageAsyncTask(callback).execute();
    }

    private abstract static class WebserviceAsyncTask<T> extends AsyncTask<Void, Void, T> {

        private WebServiceCallback<T> callback;

        private WebserviceAsyncTask(WebServiceCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        protected void onPostExecute(T t) {
            callback.onResult(t);
        }
    }

}
