package com.geoschnitzel.treasurehunt.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityNavigationDrawer;
import com.geoschnitzel.treasurehunt.map.MapContract;
import com.geoschnitzel.treasurehunt.map.MapFragment;
import com.geoschnitzel.treasurehunt.map.MapPresenter;
import com.geoschnitzel.treasurehunt.shlist.SHListContract;
import com.geoschnitzel.treasurehunt.shlist.SHListFragment;
import com.geoschnitzel.treasurehunt.shlist.SHListPresenter;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class MainActivity extends BaseActivityNavigationDrawer {

    private MapContract.Presenter mMapPresenter;
    private MapFragment mMapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mMapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (this.mMapFragment == null) {
            // Create the fragment
            this.mMapFragment = MapFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), this.mMapFragment, R.id.contentFrame);
        }

        // Create the presenter
        this.mMapPresenter = new MapPresenter(this.mMapFragment);
    }
}
