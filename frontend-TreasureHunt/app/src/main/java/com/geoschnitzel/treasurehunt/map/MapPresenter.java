package com.geoschnitzel.treasurehunt.map;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MapPresenter implements MapContract.Presenter {
    private final MapContract.View mMapView;

    public MapPresenter(@NonNull MapContract.View mapView) {
        mMapView = checkNotNull(mapView, "tasksView cannot be null!");

        mMapView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
