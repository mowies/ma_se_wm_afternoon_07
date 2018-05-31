package com.geoschnitzel.treasurehunt.base;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;

import static com.google.common.base.Preconditions.checkNotNull;

public class BasePresenter implements BaseContract.Presenter {
    private final BaseContract.View mMapView;

    public BasePresenter(@NonNull BaseContract.View mapView) {
        mMapView = checkNotNull(mapView, "tasksView cannot be null!");
        mMapView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
