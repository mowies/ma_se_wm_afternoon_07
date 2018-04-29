package com.geoschnitzel.treasurehunt.shcreatehunt;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SHCreatePresenter implements SHCreateContract.Presenter {
    private final SHCreateContract.View mView;

    public SHCreatePresenter(@NonNull SHCreateContract.View view) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
