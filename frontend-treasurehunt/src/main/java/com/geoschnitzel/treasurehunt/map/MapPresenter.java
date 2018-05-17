package com.geoschnitzel.treasurehunt.map;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SearchParamItem;

import static com.google.common.base.Preconditions.checkNotNull;

public class MapPresenter implements MapContract.Presenter {
    private final MapContract.View mView;

    private WebService webService;

    public MapPresenter(@NonNull MapContract.View view, WebService webService) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);

        this.webService = webService;
    }

    @Override
    public void start() {
        webService.retrieveHelloWorldMessage(message -> mView.showMessageText(String.format("Message: %s sent %s", message.getMessage(), message.getTimestamp())));
    }

    @Override
    public SearchParamItem GetSearchParams() {
        return new SearchParamItem("");
    }

}
