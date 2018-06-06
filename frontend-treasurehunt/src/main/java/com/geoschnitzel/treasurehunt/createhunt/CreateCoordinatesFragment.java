package com.geoschnitzel.treasurehunt.createhunt;

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

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.Coordinate;
import com.geoschnitzel.treasurehunt.rest.CreateCoordinateItem;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Collections;
import java.util.List;

public class CreateCoordinatesFragment extends Fragment implements CreateContract.ViewCoord {
    private CreateContract.Presenter mPresenter;
    private ListView coordList;

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
                    Intent picker = new PlacePicker.IntentBuilder().build(getActivity());
                    startActivity(picker);
                    mPresenter.addCoordinate(new CreateCoordinateItem(new Coordinate(4.7, 1.2)));
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
