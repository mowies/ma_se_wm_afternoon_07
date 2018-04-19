package com.geoschnitzel.treasurehunt.shpurchase;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SHPurchasePresenter implements SHPurchaseContract.Presenter {
    private final SHPurchaseContract.View mView;

    public SHPurchasePresenter(@NonNull SHPurchaseContract.View view) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public List<SHPurchaseItem> getSHPurchaseItems() {

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
