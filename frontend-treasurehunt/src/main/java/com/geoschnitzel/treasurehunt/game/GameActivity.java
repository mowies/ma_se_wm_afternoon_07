package com.geoschnitzel.treasurehunt.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class GameActivity extends BaseActivityWithBackButton {

    GameContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle params = getIntent().getExtras();
        long huntID = -1; // or other values
        if (params != null)
            huntID = params.getLong("huntID");

        Fragment contentFrame = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (contentFrame != null && huntID != -1) {
            // Create the fragment
            GameMapFragment gameMap = GameMapFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), gameMap, R.id.contentFrame);
            gameMap.setPresenter(mPresenter);


            GameHintViewFragment gameHintView = GameHintViewFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), gameMap, R.id.contentFrame);
            gameMap.setPresenter(mPresenter);

            mPresenter = new GamePresenter(gameMap, gameHintView, huntID);
        }

    }
}
