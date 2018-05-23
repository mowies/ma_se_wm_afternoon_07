package com.geoschnitzel.treasurehunt.shpurchase;

import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class SHPurchaseActivity extends BaseActivityWithBackButton {

    SHPurchaseContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SHPurchaseFragment shPurchaseFragment =
                (SHPurchaseFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (shPurchaseFragment == null) {
            // Create the fragment
            shPurchaseFragment = SHPurchaseFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), shPurchaseFragment, R.id.contentFrame);
        }

        mPresenter = new SHPurchasePresenter(shPurchaseFragment, WebService.instance());
    }
}
