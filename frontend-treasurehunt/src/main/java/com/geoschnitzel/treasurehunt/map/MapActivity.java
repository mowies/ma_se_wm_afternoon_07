package com.geoschnitzel.treasurehunt.map;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityNavigationDrawer;
import com.geoschnitzel.treasurehunt.rest.SearchParamItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapActivity extends BaseActivityNavigationDrawer implements
        MapContract.View,
        OnMapReadyCallback,
        View.OnClickListener {

    MapContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_map);
        mPresenter = new MapPresenter(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingSearchButton);
        fab.setOnClickListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingSearchButton:
                OpenSearch(mPresenter.GetSearchParams());
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void OpenSearch(SearchParamItem sParam) {

        Snackbar.make(findViewById(R.id.drawer_layout), "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
