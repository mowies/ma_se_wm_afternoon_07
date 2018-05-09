package com.geoschnitzel.treasurehunt.login;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View mView;

    public LoginPresenter(@NonNull LoginContract.View view) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
