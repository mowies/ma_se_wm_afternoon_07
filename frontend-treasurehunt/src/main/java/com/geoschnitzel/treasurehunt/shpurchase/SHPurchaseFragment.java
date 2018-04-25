package com.geoschnitzel.treasurehunt.shpurchase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.geoschnitzel.treasurehunt.R;

public class SHPurchaseFragment extends Fragment implements SHPurchaseContract.View {
    private SHPurchaseContract.Presenter mPresenter;

    public static SHPurchaseFragment newInstance() {
        return new SHPurchaseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shpurchase, container, false);

        GridView glshpurchase = root.findViewById(R.id.shpurchase_gvitems);
        glshpurchase.setAdapter(new SHPurchaseAdapter(mPresenter.getSHPurchaseItems(), getActivity().getApplicationContext()));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public void setPresenter(SHPurchaseContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
