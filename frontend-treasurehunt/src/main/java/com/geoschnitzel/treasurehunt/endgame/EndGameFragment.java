package com.geoschnitzel.treasurehunt.endgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geoschnitzel.treasurehunt.R;

public class EndGameFragment extends Fragment implements EndGameContract.View {
    private EndGameContract.Presenter mPresenter = null;

    public static EndGameFragment newInstance() {
        return new EndGameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_end_game, container, false);
    }

    @Override
    public void setPresenter(EndGameContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
