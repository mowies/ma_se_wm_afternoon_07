package com.geoschnitzel.treasurehunt.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private BottomSheetBehavior mBottomSheetBehavior = null;

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
        this.mSHListFragment = (SHListFragment) fm.findFragmentById(R.id.sh_list_fragment_content);

        if (this.mSHListFragment == null) {
            this.mSHListFragment = SHListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, this.mSHListFragment, R.id.main_sh_list_fragment);
            this.mSHListPresenter = new SHListPresenter(this.mSHListFragment);
        }

        View temp = root.findViewById(R.id.main_sh_list_fragment);
        this.mBottomSheetBehavior = BottomSheetBehavior.from(temp);

        if (this.mBottomSheetBehavior != null){
            this.mBottomSheetBehavior.setHideable(false);
            this.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            this.mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override public void onStateChanged(@NonNull final View bottomSheet, final int newState) {
                    getActivity().invalidateOptionsMenu();

//                    if (newState == BottomSheetBehavior.STATE_COLLAPSED){
//                        final ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.sh_list_fragment_content);
//                        final ConstraintLayout.LayoutParams layoutParams =  new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.MATCH_PARENT);
//                        layoutParams.setMargins(0,0,0,0);
//                        layout.setLayoutParams(layoutParams);
//                        layout.requestLayout();
//
//                    }
//                    if (newState == BottomSheetBehavior.STATE_EXPANDED){
//
//                        final ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.sh_list_fragment_content);
//                        final ConstraintLayout.LayoutParams layoutParams =  new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.MATCH_PARENT);
//                        final int toolbarSize = findViewById(R.id.toolbar).getHeight();
//                        layoutParams.setMargins(0,toolbarSize,0,0);
//                        layout.setLayoutParams(layoutParams);
//                        layout.requestLayout();
//                    }
                }

                @Override public void onSlide(@NonNull final View bottomSheet, final float slideOffset) {
//                    final float scaleFactor = 1 - slideOffset;
//                    if (mFab != null){
//                        if (scaleFactor <= 1){
//                            mFab.setVisibility(View.VISIBLE);
//                            mFab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
//                        }
//                        if (slideOffset == 1.00f){
//                            mFab.setVisibility(View.INVISIBLE);
//                        }
//                    }
                }
            });
        }
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
