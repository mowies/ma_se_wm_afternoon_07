package com.geoschnitzel.treasurehunt.main;

import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityNavigationDrawer;
import com.geoschnitzel.treasurehunt.map.MapContract;
import com.geoschnitzel.treasurehunt.map.MapFragment;
import com.geoschnitzel.treasurehunt.map.MapPresenter;
import com.geoschnitzel.treasurehunt.model.WebserviceProvider;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class MainActivity extends BaseActivityNavigationDrawer {

    private MapContract.Presenter mMapPresenter;
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
        this.mMapPresenter = new MapPresenter(mapFragment, WebserviceProvider.getWebservice());
    }
}
