package com.geoschnitzel.treasurehunt.map;

import android.os.Bundle;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityNavigationDrawer;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class MapActivity extends BaseActivityNavigationDrawer {

    private IBasePresenter mPresenter;
    private MapContract.View mMapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mapFragment == null) {
            // Create the fragment
            mapFragment = MapFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mapFragment, R.id.contentFrame);
        }

        // Create the presenter
        this.mPresenter = new MapPresenter(mapFragment);
    }
}
