package com.geoschnitzel.treasurehunt.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geoschnitzel.treasurehunt.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class GameMapFragment extends android.support.v4.app.Fragment implements GameContract.MapView, OnMapReadyCallback {
    private GameContract.Presenter mPresenter;

    public static GameMapFragment newInstance() {
        return new GameMapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return root;
    }

    @Override
    public void setPresenter(GameContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
