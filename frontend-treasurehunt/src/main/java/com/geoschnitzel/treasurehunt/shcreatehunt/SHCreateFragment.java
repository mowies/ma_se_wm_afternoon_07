package com.geoschnitzel.treasurehunt.shcreatehunt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.geoschnitzel.treasurehunt.R;

public class SHCreateFragment extends Fragment implements SHCreateContract.View {
    private SHCreateContract.Presenter mPresenter;

    public static SHCreateFragment newInstance() {
        return new SHCreateFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shcreatehunt, container, false);


        return root;
    }

    @Override
    public void setPresenter(SHCreateContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
