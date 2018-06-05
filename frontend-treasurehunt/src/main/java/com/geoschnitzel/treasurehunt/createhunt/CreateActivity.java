package com.geoschnitzel.treasurehunt.createhunt;

import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class CreateActivity extends BaseActivityWithBackButton {

    CreateContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CreateMainFragment createMainFragment =
                (CreateMainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (createMainFragment == null) {
            // Create the fragment
            createMainFragment = CreateMainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), createMainFragment, R.id.contentFrame);
        }

        mPresenter = new CreatePresenter(createMainFragment);
    }
}
