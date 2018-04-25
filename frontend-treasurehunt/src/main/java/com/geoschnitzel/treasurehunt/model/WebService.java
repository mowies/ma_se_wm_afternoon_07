package com.geoschnitzel.treasurehunt.model;

import android.os.AsyncTask;

import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.geoschnitzel.treasurehunt.rest.Message;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class WebService {
    public static void retrieveSHPurchaseItems(WebServiceCallback<List<SHPurchaseItem>> callback) {
        new WebserviceAsyncTask<List<SHPurchaseItem>>(callback) {
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
        }.execute();
    }

    public static void retrieveSHListItems(WebServiceCallback<List<SHListItem>> callback) {
        new WebserviceAsyncTask<List<SHListItem>>(callback) {
            @Override
            protected List<SHListItem> doInBackground(Void... voids) {
                List<SHListItem> testList = new ArrayList<SHListItem>();
                testList.add(new SHListItem("Graz", "Dominik Adelbert", 420f, 4.2f, "Lorem ipsum dolor sit amet, principes ", false));
                testList.add(new SHListItem("Kärnten", "Yvonne Wendelin", 635f, 4.7f, "gubergren ei, vis id consul", true));
                testList.add(new SHListItem("Lienz", "Heidi Niko", 507f, 1.5f, "Sea vidit maiorum nostrum no. ", true));
                testList.add(new SHListItem("Linz", "Ingrid Margarethe", 752f, 3.1f, " Est ea minim scripta dissentiet", false));
                testList.add(new SHListItem("Brixen", "Burkhard Walter", 109f, 2.0f, "Лорем ипсум долор сит амет, пер цлита поссит ех, ат мунере", true));
                testList.add(new SHListItem("Wien", "Diederich Claudia", 614f, 0.3f, " Est ea minim scripta dissentiet", false));
                testList.add(new SHListItem("München", "Wolf Elias", 120f, 3.5f, " Est ea minim scripta dissentiet", false));
                testList.add(new SHListItem("Leoben", "Debora Conrad", 475f, 1.6f, " Est ea minim scripta dissentiet", true));
                return testList;
            }
        }.execute();
    }

    public static void retrieveHelloWorldMessage(final WebServiceCallback<Message> callback) {
        new WebserviceAsyncTask<Message>(callback) {
            @Override
            protected Message doInBackground(Void... voids) {
                String url = "http://10.0.2.2:8080/helloWorld";

                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().registerModule(new KotlinModule());
                restTemplate.getMessageConverters().add(converter);

                return restTemplate.getForObject(url, Message.class);
            }
        }.execute();
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

    public interface WebServiceCallback<T> {
        void onResult(T result);
    }
}
