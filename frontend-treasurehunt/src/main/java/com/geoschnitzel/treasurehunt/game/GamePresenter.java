package com.geoschnitzel.treasurehunt.game;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GamePresenter implements GameContract.Presenter {
    private GameContract.MapView mapView = null;
    private GameContract.HintView hintView = null;
    private GameItem game = null;


    public GamePresenter(@NonNull GameContract.MapView mapView, GameContract.HintView hintView, long huntID) {
        this.mapView = checkNotNull(mapView, "mapView cannot be null!");
        this.hintView = checkNotNull(hintView, "hintView cannot be null!");
        this.mapView.setPresenter(this);
        this.hintView.setPresenter(this);
        game = WebService.startGame(huntID);
    }

    @Override
    public void start() {
    }


    @Override
    public GameItem getCurrentGame() {
        return game;
    }

    @Override
    public void fetchHints() {
        List<HintItem> hints = WebService.FetchHints();
        game.getCurrentTarget().setHints(hints);
        hintView.ReloadHints(hints);
    }

    @Override
    public void buyHint(long hintID) {

    }
}
