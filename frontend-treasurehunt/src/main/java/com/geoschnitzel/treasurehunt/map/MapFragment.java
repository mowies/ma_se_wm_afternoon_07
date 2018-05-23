package com.geoschnitzel.treasurehunt.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.SearchParamItem;
import com.geoschnitzel.treasurehunt.shlist.SHListContract;
import com.geoschnitzel.treasurehunt.shlist.SHListFragment;
import com.geoschnitzel.treasurehunt.shlist.SHListPresenter;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback, View.OnClickListener {
    private MapContract.Presenter mPresenter;
    private SHListContract.Presenter mSHListPresenter;
    private SHListFragment mSHListFragment;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.floatingSearchButton);
        fab.setOnClickListener(this);

        this.setUpBottomSheetFragment(root);

        return root;
    }



    private void setUpBottomSheetFragment(View root) {
        final FragmentManager fm = getChildFragmentManager();
        this.mSHListFragment = (SHListFragment) fm.findFragmentById(R.id.main_sh_list_fragment);

        if (this.mSHListFragment == null) {
            this.mSHListFragment = SHListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, this.mSHListFragment, R.id.bottom_sheet_content_frame, getString(R.string.fragment_tag_shlist));
            this.mSHListPresenter = new SHListPresenter(this.mSHListFragment);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void openSearch(SearchParamItem sParam) {
        Snackbar.make(getActivity().findViewById(R.id.drawer_layout), "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void showMessageText(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingSearchButton:
                this.openSearch(((MapContract.Presenter) mPresenter).GetSearchParams());
                break;
        }
    }
}
