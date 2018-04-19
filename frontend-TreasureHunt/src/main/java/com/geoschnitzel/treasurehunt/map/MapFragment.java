package com.geoschnitzel.treasurehunt.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.SearchParamItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback, View.OnClickListener {
    private MapContract.Presenter mPresenter;

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
        return root;
    }

    @Override
    public void openSearch(SearchParamItem sParam) {
        Snackbar.make(getActivity().findViewById(R.id.drawer_layout), "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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