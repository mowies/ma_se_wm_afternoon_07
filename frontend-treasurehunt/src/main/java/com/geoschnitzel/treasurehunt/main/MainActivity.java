package com.geoschnitzel.treasurehunt.main;

import android.net.Uri;
import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityNavigationDrawer;
import com.geoschnitzel.treasurehunt.map.MapContract;
import com.geoschnitzel.treasurehunt.map.MapFragment;
import com.geoschnitzel.treasurehunt.map.MapPresenter;
import com.geoschnitzel.treasurehunt.shfilter.SHFilterFragment;
import com.geoschnitzel.treasurehunt.shlist.SHListFragment;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class MainActivity extends BaseActivityNavigationDrawer implements SHFilterFragment.OnFragmentInteractionListener {

    private MapContract.Presenter mMapPresenter;
    private MapFragment mMapFragment;
    private SHListFragment mShListFragment;

    @Override
    public void onBackPressed() {
        mShListFragment = (SHListFragment) mMapFragment.getChildFragmentManager().findFragmentByTag(getString(R.string.fragment_tag_shlist));

        if (mShListFragment != null) {
            mShListFragment.closeBottomSheet();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mMapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (this.mMapFragment == null) {
            // Create the fragment
            this.mMapFragment = MapFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), this.mMapFragment, R.id.contentFrame, getString(R.string.fragment_tag_map));
        }

        // Create the presenter
        this.mMapPresenter = new MapPresenter(this.mMapFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
