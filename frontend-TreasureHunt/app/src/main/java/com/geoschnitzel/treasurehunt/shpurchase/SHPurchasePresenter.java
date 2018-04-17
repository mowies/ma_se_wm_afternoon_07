package com.geoschnitzel.treasurehunt.shpurchase;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class SHPurchasePresenter implements SHPurchaseContract.Presenter {
    private final SHPurchaseContract.View mMapView;

    public SHPurchasePresenter(@NonNull SHPurchaseContract.View mapView) {
        mMapView = checkNotNull(mapView, "tasksView cannot be null!");

        mMapView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
