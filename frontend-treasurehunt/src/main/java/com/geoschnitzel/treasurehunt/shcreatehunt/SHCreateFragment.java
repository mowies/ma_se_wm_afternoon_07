package com.geoschnitzel.treasurehunt.shcreatehunt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

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
        ImageButton image = (ImageButton) root.findViewById(R.id.new_hunt_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }
        });

        Button forward = (Button) root.findViewById(R.id.new_hunt_next);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button abort = (Button) root.findViewById(R.id.new_hunt_abort);
        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    public void setPresenter(SHCreateContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
