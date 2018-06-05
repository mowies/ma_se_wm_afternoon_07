package com.geoschnitzel.treasurehunt.createhunt;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class CreatePresenter implements CreateContract.Presenter {
    private final CreateContract.View mView;

    public CreatePresenter(@NonNull CreateContract.View view) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
