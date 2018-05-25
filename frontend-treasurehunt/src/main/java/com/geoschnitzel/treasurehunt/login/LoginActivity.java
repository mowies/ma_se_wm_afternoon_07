package com.geoschnitzel.treasurehunt.login;

import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class LoginActivity extends BaseActivityWithBackButton {

    LoginContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.contentFrame, getString(R.string.fragment_tag_login));
        }

        mPresenter = new LoginPresenter(loginFragment);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}
