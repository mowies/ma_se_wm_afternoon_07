package com.geoschnitzel.treasurehunt.shlist;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SHListPresenter implements SHListContract.Presenter {
    private final SHListContract.View mView;

    public SHListPresenter(@NonNull SHListContract.View view) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void retrieveSHListItems(WebService.WebServiceCallback<List<SHListItem>> callback) {
        WebService.retrieveSHListItems(callback);
    }
}
