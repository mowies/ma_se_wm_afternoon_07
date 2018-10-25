package com.geoschnitzel.treasurehunt.createhunt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.CreateCoordinateItem;

import java.util.List;

public class CreateMainFragment extends Fragment implements CreateContract.View {
    private CreateContract.Presenter mPresenter;

    public static CreateMainFragment newInstance() {
        return new CreateMainFragment();
    }

    private CreateCoordinatesFragment createCoordinatesFragment = null;
    private CreateHuntFragment createHuntFragment = null;
    private Fragment currentChildFragment = null;

    private Button button_positive;
    private Button button_negative;

    private void setChildFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.createContentFrame, fragment, "NewFragmentTag");
        ft.commit();
        currentChildFragment = fragment;
    }

    private void setHuntFragment() {
        if (createHuntFragment == null) {
            createHuntFragment = new CreateHuntFragment();
            createHuntFragment.setPresenter(mPresenter);
        }
        button_positive.setText(R.string.forward);
        button_negative.setText(R.string.abort);
        setChildFragment(createHuntFragment);
    }
    private void setCoordinatesFragment() {
        if (createCoordinatesFragment == null) {
            createCoordinatesFragment = new CreateCoordinatesFragment();
            createCoordinatesFragment.setPresenter(mPresenter);
        }
        button_positive.setText(R.string.create_hunt);
        button_negative.setText(R.string.go_back);
        setChildFragment(createCoordinatesFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_main, container, false);

        button_positive = (Button) root.findViewById(R.id.bottombar_positive);
        button_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentChildFragment == createHuntFragment) {
                    setCoordinatesFragment();
                } else if (currentChildFragment == createCoordinatesFragment) {
                    //Create hunt
                }
            }
        });

        button_negative = (Button) root.findViewById(R.id.bottombar_negative);
        button_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentChildFragment == createHuntFragment) {
                    getActivity().finish();
                } else if (currentChildFragment == createCoordinatesFragment) {
                    setHuntFragment();
                }
            }
        });

        setHuntFragment();
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

    @Override
    public void refreshCoordList(List<CreateCoordinateItem> items) {
        if (createCoordinatesFragment != null)
            createCoordinatesFragment.refreshCoordList(items);
    }
}
