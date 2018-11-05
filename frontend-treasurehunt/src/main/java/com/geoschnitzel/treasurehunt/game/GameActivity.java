package com.geoschnitzel.treasurehunt.game;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class GameActivity extends BaseActivityWithBackButton {

    GameContract.Presenter mPresenter;
    public GameItem getGame() { return mPresenter.getCurrentGame(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle params = getIntent().getExtras();
        long huntID = -1; // or other values
        if (params != null)
            huntID = params.getLong("huntID");

        FragmentManager manager = getSupportFragmentManager();
        //Check if Fragments arent generated yet
        GameMapFragment gameMap = (GameMapFragment) manager.findFragmentByTag(R.id.fragment_game_map + "");
        GameHintViewFragment gameHintView = (GameHintViewFragment) manager.findFragmentByTag(R.id.fragment_game_hintview + "");

        if (gameMap == null && gameHintView == null && huntID != -1) {

            // Create the fragments
            gameMap = GameMapFragment.newInstance();
            gameHintView = GameHintViewFragment.newInstance();

            //Add Fragments into contentFrame
            ActivityUtils.addFragmentToActivity(manager, gameMap, R.id.contentFrame, R.id.fragment_game_map + "");
            ActivityUtils.addFragmentToActivity(manager, gameHintView, R.id.contentFrame, R.id.fragment_game_hintview + "");
        }

        if (gameMap != null && gameHintView != null) {
            mPresenter = new GamePresenter(gameMap, gameHintView, huntID, WebService.instance());
        }
    }
}
