package com.geoschnitzel.treasurehunt.map;

import android.location.Location;
import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SearchParamItem;

import static com.google.common.base.Preconditions.checkNotNull;

public class MapPresenter implements MapContract.Presenter {
    private final MapContract.View mView;
    private final WebService webService;

    public MapPresenter(@NonNull MapContract.View view, @NonNull WebService webService) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        this.webService = webService;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public SearchParamItem getSearchParams() {
        return new SearchParamItem("");
    }



}
