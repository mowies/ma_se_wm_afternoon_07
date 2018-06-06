package com.geoschnitzel.treasurehunt.shlist;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.utils.Webservice.WebServiceCallback;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

public class SHListPresenter implements SHListContract.Presenter {
    private final SHListContract.View mView;

    private WebService webService;

    public SHListPresenter(@NonNull SHListContract.View view, WebService webService) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);

        this.webService = webService;
    }

    @Override
    public void start() {

    }

    @Override
    public void retrieveSHListItems() {
        webService.getSHListItems(result -> mView.refreshSHListAdapter(Arrays.asList(result)));
    }
}
