package com.geoschnitzel.treasurehunt.shcreatehunt;

import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class SHCreateActivity extends BaseActivityWithBackButton {

    SHCreateContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SHCreateFragment shCreateFragment =
                (SHCreateFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (shCreateFragment == null) {
            // Create the fragment
            shCreateFragment = SHCreateFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), shCreateFragment, R.id.contentFrame);
        }

        mPresenter = new SHCreatePresenter(shCreateFragment);
    }
}
