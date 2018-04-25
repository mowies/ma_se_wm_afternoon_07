package com.geoschnitzel.treasurehunt.shlist;

import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class SHListActivity extends BaseActivityWithBackButton{

    SHListContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SHListFragment shListFragment =
                (SHListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(shListFragment == null){
            shListFragment = SHListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), shListFragment, R.id.contentFrame);
        }
        mPresenter = new SHListPresenter(shListFragment);
    }

}
