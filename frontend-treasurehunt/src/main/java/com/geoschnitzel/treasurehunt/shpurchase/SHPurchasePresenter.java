package com.geoschnitzel.treasurehunt.shpurchase;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

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
        return WebService.getSHPurchaseItems();
    }
}
