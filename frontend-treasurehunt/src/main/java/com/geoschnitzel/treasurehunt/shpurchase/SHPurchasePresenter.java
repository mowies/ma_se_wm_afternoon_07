package com.geoschnitzel.treasurehunt.shpurchase;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SHPurchasePresenter implements SHPurchaseContract.Presenter {
    private final SHPurchaseContract.View mView;

    private WebService webService;

    public SHPurchasePresenter(@NonNull SHPurchaseContract.View view, WebService webService) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);

        this.webService = webService;
    }

    @Override
    public void start() {

    }

    @Override
    public void retrieveSHPurchaseItems(WebService.WebServiceCallback<List<SHPurchaseItem>> callback) {
        webService.retrieveSHPurchaseItems(callback);
    }

}
