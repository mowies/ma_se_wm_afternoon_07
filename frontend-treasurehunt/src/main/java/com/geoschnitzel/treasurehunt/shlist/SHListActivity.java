package com.geoschnitzel.treasurehunt.shlist;

import android.net.Uri;
import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.model.WebserviceProvider;
import com.geoschnitzel.treasurehunt.shfilter.SHFilterFragment;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class SHListActivity extends BaseActivityWithBackButton implements SHFilterFragment.OnFragmentInteractionListener{

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
        mPresenter = new SHListPresenter(shListFragment, WebserviceProvider.getWebservice());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}
