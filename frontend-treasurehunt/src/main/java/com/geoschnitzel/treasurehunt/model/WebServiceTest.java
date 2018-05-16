package com.geoschnitzel.treasurehunt.model;

import com.geoschnitzel.treasurehunt.rest.Message;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebServiceTest implements WebService {
    private static WebServiceTest instance = new WebServiceTest();

    public static WebService instance() {
        return instance;
    }

    @Override
    public void retrieveSHPurchaseItems(WebServiceCallback<List<SHPurchaseItem>> callback) {
        List<SHPurchaseItem> testList = new ArrayList<SHPurchaseItem>();
        testList.add(new SHPurchaseItem(10, 2.99f, 1, "$", "{0} {1}", "Bronze"));
        testList.add(new SHPurchaseItem(20, 4.99f, 1, "$", "{0} {1}", "Silber"));
        testList.add(new SHPurchaseItem(100, 7.99f, 1, "$", "{0} {1}", "Gold"));
        testList.add(new SHPurchaseItem(500, 12.99f, 1, "$", "{0} {1}", "Platin"));
        testList.add(new SHPurchaseItem(1000, 17.99f, 1, "$", "{0} {1}", "Rodium"));
        testList.add(new SHPurchaseItem(5000, 24.99f, 1, "$", "{0} {1}", "Plutonium"));

        callback.onResult(testList);
    }

    @Override
    public void retrieveSHListItems(WebServiceCallback<List<SHListItem>> callback) {
        List<SHListItem> testList = new ArrayList<SHListItem>();
        testList.add(new SHListItem("Graz", "Dominik Adelbert", 420f, 4.2f, "Lorem ipsum dolor sit amet, principes ", false));
        testList.add(new SHListItem("Kärnten", "Yvonne Wendelin", 635f, 4.7f, "gubergren ei, vis id consul", true));
        testList.add(new SHListItem("Lienz", "Heidi Niko", 507f, 1.5f, "Sea vidit maiorum nostrum no. ", true));
        testList.add(new SHListItem("Linz", "Ingrid Margarethe", 752f, 3.1f, " Est ea minim scripta dissentiet", false));
        testList.add(new SHListItem("Brixen", "Burkhard Walter", 109f, 2.0f, "Лорем ипсум долор сит амет, пер цлита поссит ех, ат мунере", true));
        testList.add(new SHListItem("Wien", "Diederich Claudia", 614f, 0.3f, " Est ea minim scripta dissentiet", false));
        testList.add(new SHListItem("München", "Wolf Elias", 120f, 3.5f, " Est ea minim scripta dissentiet", false));
        testList.add(new SHListItem("Leoben", "Debora Conrad", 475f, 1.6f, " Est ea minim scripta dissentiet", true));

        callback.onResult(testList);
    }

    @Override
    public void retrieveHelloWorldMessage(final WebServiceCallback<Message> callback) {
        callback.onResult(new Message("Hello world (from test webservice)", new Date(1524066200885L)));
    }

}
