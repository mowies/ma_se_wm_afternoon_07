package com.geoschnitzel.treasurehunt.map;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.rest.SearchParamItem;

import static com.google.common.base.Preconditions.checkNotNull;

public class MapPresenter implements MapContract.Presenter {
    private final MapContract.View mView;

    public MapPresenter(@NonNull MapContract.View view) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public SearchParamItem GetSearchParams() {
        return new SearchParamItem("");
    }
}