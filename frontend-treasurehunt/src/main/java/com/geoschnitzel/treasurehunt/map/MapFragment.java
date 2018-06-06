package com.geoschnitzel.treasurehunt.map;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SearchParamItem;
import com.geoschnitzel.treasurehunt.shlist.SHListContract;
import com.geoschnitzel.treasurehunt.shlist.SHListFragment;
import com.geoschnitzel.treasurehunt.shlist.SHListPresenter;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static android.content.ContentValues.TAG;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback, View.OnClickListener {
    private MapContract.Presenter mPresenter;
    private SHListContract.Presenter mSHListPresenter;
    private SHListFragment mSHListFragment;
    private GoogleMap mMap;
    private Boolean mLocationPermissionGranted = false;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private LocationServices mLastKnownLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


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

        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity(), null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        return root;
    }

    private void setUpBottomSheetFragment(View root) {
        final FragmentManager fm = getChildFragmentManager();
        this.mSHListFragment = (SHListFragment) fm.findFragmentById(R.id.main_sh_list_fragment);

        if (this.mSHListFragment == null) {
            this.mSHListFragment = SHListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, this.mSHListFragment, R.id.bottom_sheet_content_frame, getString(R.string.fragment_tag_shlist));
            this.mSHListPresenter = new SHListPresenter(this.mSHListFragment, WebService.instance());
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
        mMap = googleMap;

        updateLocationUI();
        getDeviceLocation();
    }

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<LocationServices>() {
                    @Override
                    public void onComplete(@NonNull Task<LocationServices> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
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
