package com.geoschnitzel.treasurehunt.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.HintItem;

import java.util.List;

public class GameHintViewFragment extends Fragment implements GameContract.HintView {
    private GameContract.Presenter mPresenter;
    private View root = null;

    public static GameHintViewFragment newInstance() {
        return new GameHintViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_game_hintview, container, false);
        return root;
    }

    @Override
    public void setPresenter(GameContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void ReloadHints(List<HintItem> hints) {

        HorizontalGridView glshpurchase = root.findViewById(R.id.hgvhint);
        glshpurchase.setAdapter(new GameHintAdapter(hints, getActivity().getApplicationContext(), mPresenter));
        // This needed to be added
        HorizontalGridView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        glshpurchase.setLayoutManager(layoutManager);
        glshpurchase.setHasFixedSize(true);
    }
}

