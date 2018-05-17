package com.geoschnitzel.treasurehunt.model;

import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.GameTargetItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.HintType;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WebService {

    private static UserItem currentUser = null;

    public static UserItem getLoggedInUser() {
        if (currentUser == null)
            currentUser = new UserItem(0);
        return currentUser;
    }
    public static List<SHPurchaseItem> getSHPurchaseItems() {

        List<SHPurchaseItem> testList = new ArrayList<>();
        testList.add(new SHPurchaseItem(10, 2.99f, 1, "$", "{0} {1}", "Bronze"));
        testList.add(new SHPurchaseItem(20, 4.99f, 1, "$", "{0} {1}", "Silber"));
        testList.add(new SHPurchaseItem(100, 7.99f, 1, "$", "{0} {1}", "Gold"));
        testList.add(new SHPurchaseItem(500, 12.99f, 1, "$", "{0} {1}", "Platin"));
        testList.add(new SHPurchaseItem(1000, 17.99f, 1, "$", "{0} {1}", "Rodium"));
        testList.add(new SHPurchaseItem(5000, 24.99f, 1, "$", "{0} {1}", "Plutonium"));

        return testList;
    }


    public static List<SHListItem> getSHListItems() {

        List<SHListItem> testList = new ArrayList<>();
        testList.add(new SHListItem(0, "Graz", "Dominik Adelbert", 420f, 4.2f, "Lorem ipsum dolor sit amet, principes ", false));
        testList.add(new SHListItem(1, "Kärnten", "Yvonne Wendelin", 635f, 4.7f, "gubergren ei, vis id consul", true));
        testList.add(new SHListItem(2, "Lienz", "Heidi Niko", 507f, 1.5f, "Sea vidit maiorum nostrum no. ", true));
        testList.add(new SHListItem(3, "Linz", "Ingrid Margarethe", 752f, 3.1f, " Est ea minim scripta dissentiet", false));
        testList.add(new SHListItem(4, "Brixen", "Burkhard Walter", 109f, 2.0f, "Лорем ипсум долор сит амет, пер цлита поссит ех, ат мунере", true));
        testList.add(new SHListItem(5, "Wien", "Diederich Claudia", 614f, 0.3f, " Est ea minim scripta dissentiet", false));
        testList.add(new SHListItem(6, "München", "Wolf Elias", 120f, 3.5f, " Est ea minim scripta dissentiet", false));
        testList.add(new SHListItem(7, "Leoben", "Debora Conrad", 475f, 1.6f, " Est ea minim scripta dissentiet", true));
        return testList;
    }

    public static List<HintItem> FetchHints() {
        return Arrays.asList(
                new HintItem(0, HintType.TEXT, 0, 0, true, "Hint #0", null, null, null),
                new HintItem(1, HintType.TEXT, 10, 10, false, "Hint #1", null, null, null),
                new HintItem(2, HintType.IMAGE, 20, 20, false, null, "ccacb863-5897-485b-b822-ca119c7afcfb", null, null),

                new HintItem(3, HintType.DIRECTION, 50, 30, false, "Hint #2", null, null, null),

                new HintItem(4, HintType.COORDINATE, 100, 40, false, "Hint #2", null, null, null)
        );
    }

    public static GameItem startGame(long huntID) {
        //Send current User
        //Check if user is logged in
        return new GameItem(0, new GameTargetItem(0, new Date(), FetchHints()));
    }
}
