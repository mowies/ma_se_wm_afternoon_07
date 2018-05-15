package com.geoschnitzel.treasurehunt.model;

import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.GameTargetItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.HintType;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WebService {
    public static List<SHPurchaseItem> getSHPurchaseItems() {

        List<SHPurchaseItem> testList = new ArrayList<SHPurchaseItem>();
        testList.add(new SHPurchaseItem(10, 2.99f, 1, "$", "{0} {1}", "Bronze"));
        testList.add(new SHPurchaseItem(20, 4.99f, 1, "$", "{0} {1}", "Silber"));
        testList.add(new SHPurchaseItem(100, 7.99f, 1, "$", "{0} {1}", "Gold"));
        testList.add(new SHPurchaseItem(500, 12.99f, 1, "$", "{0} {1}", "Platin"));
        testList.add(new SHPurchaseItem(1000, 17.99f, 1, "$", "{0} {1}", "Rodium"));
        testList.add(new SHPurchaseItem(5000, 24.99f, 1, "$", "{0} {1}", "Plutonium"));

        return testList;
    }


    public static List<SHListItem> getSHListItems() {

        List<SHListItem> testList = new ArrayList<SHListItem>();
        testList.add(new SHListItem("Graz","Dominik Adelbert",420f,4.2f,"Lorem ipsum dolor sit amet, principes ",false));
        testList.add(new SHListItem("Kärnten","Yvonne Wendelin",635f,4.7f,"gubergren ei, vis id consul",true));
        testList.add(new SHListItem("Lienz","Heidi Niko",507f,1.5f,"Sea vidit maiorum nostrum no. ",true));
        testList.add(new SHListItem("Linz","Ingrid Margarethe",752f,3.1f," Est ea minim scripta dissentiet",false));
        testList.add(new SHListItem("Brixen","Burkhard Walter",109f,2.0f,"Лорем ипсум долор сит амет, пер цлита поссит ех, ат мунере",true));
        testList.add(new SHListItem("Wien","Diederich Claudia",614f,0.3f," Est ea minim scripta dissentiet",false));
        testList.add(new SHListItem("München","Wolf Elias",120f,3.5f," Est ea minim scripta dissentiet",false));
        testList.add(new SHListItem("Leoben","Debora Conrad",475f,1.6f," Est ea minim scripta dissentiet",true));
        return testList;
    }

    public static List<HintItem> FetchHints() {
        return Arrays.asList(
                new HintItem(0, HintType.TEXT, 0, 0, true, "Hint #0", null, null, null),
                new HintItem(1, HintType.TEXT, 10, 60, true, "Hint #1", null, null, null),
                new HintItem(2, HintType.IMAGE, 20, 120, true, null, "ccacb863-5897-485b-b822-ca119c7afcfb", null, null),

                new HintItem(3, HintType.DIRECTION, 50, 1000, true, "Hint #2", null, null, null),

                new HintItem(4, HintType.COORDINATE, 100, 2000, true, "Hint #2", null, null, null)
        );
    }

    public static GameItem startGame(long userID, long huntID) {
        return new GameItem(0, new GameTargetItem(0, new Date(), FetchHints()));
    }
}
