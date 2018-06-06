package com.geoschnitzel.treasurehunt.game;

import android.location.Location;
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
    private WebService webService = null;


    public GamePresenter(@NonNull GameContract.MapView mapView, GameContract.HintView hintView, long huntID,WebService webService) {
        this.mapView = checkNotNull(mapView, "mapView cannot be null!");
        this.hintView = checkNotNull(hintView, "hintView cannot be null!");
        this.mapView.setPresenter(this);
        this.hintView.setPresenter(this);
        this.webService = webService;
        webService.startGame(result -> {
            game = result;
            List<HintItem> hints = result.getCurrenttarget().getHints();
            hintView.ReloadHints(hints);
        },huntID);
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
        webService.getGame(result -> {
            game = result;
            List<HintItem> hints = game.getCurrenttarget().getHints();
            hintView.ReloadHints(hints);
        },game.getId());
    }


    @Override
    public void buyHint(long hintID) {
        webService.buyHint(result -> {
            //Reload Balance
            webService.getUser(result1 -> fetchHints());
        },game.getId(),hintID);
    }

    @Override
    public void unlockHint(long hintID) {
        webService.unlockHint(result -> fetchHints(),game.getId(),hintID);
    }

    @Override
    public void sendUserLocation(Location mLastKnownLocation) {
        webService.sendUserLocation(mLastKnownLocation,game.getId());
    }
}
