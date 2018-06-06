package com.geoschnitzel.treasurehunt.createhunt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.Coordinate;
import com.geoschnitzel.treasurehunt.rest.CreateCoordinateItem;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CreateCoordinatesFragment extends Fragment implements CreateContract.ViewCoord {
    private CreateContract.Presenter mPresenter;
    private ListView coordList;
    final int PLACE_PICKER_REQUEST = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                mPresenter.addCoordinate(new CreateCoordinateItem(place.getAddress().toString(), new Coordinate(place.getLatLng().longitude, place.getLatLng().latitude)));
            }
        }
    }


    public static CreateMainFragment newInstance() {
        return new CreateMainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_coordinates, container, false);

        coordList = root.findViewById(R.id.shcoordinate_list);
        refreshCoordList(Collections.emptyList());

        FloatingActionButton fab = root.findViewById(R.id.create_coord_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    Intent picker = builder.build(getActivity());
                    startActivityForResult(picker, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(CreateContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    public void refreshCoordList(List<CreateCoordinateItem> items) {
        CreateCoordinatesAdapter adapter = new CreateCoordinatesAdapter(items, getActivity().getApplicationContext());
        coordList.setAdapter(adapter);
    }
}
