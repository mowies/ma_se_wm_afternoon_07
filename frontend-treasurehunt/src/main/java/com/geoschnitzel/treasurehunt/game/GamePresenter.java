package com.geoschnitzel.treasurehunt.game;

import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingResource;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.UserItem;
import com.geoschnitzel.treasurehunt.utils.SimpleIdlingResource;
import com.geoschnitzel.treasurehunt.utils.Webservice.WebServiceCallback;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GamePresenter implements GameContract.Presenter {
    private GameContract.MapView mapView = null;
    private GameContract.HintView hintView = null;
    private GameItem game = null;
    private WebService webService = null;
    private SimpleIdlingResource mIdlingResource;


    public GamePresenter(@NonNull GameContract.MapView mapView, GameContract.HintView hintView, long huntID,WebService webService) {
        mIdlingResource = new SimpleIdlingResource();
        this.mapView = checkNotNull(mapView, "mapView cannot be null!");
        this.hintView = checkNotNull(hintView, "hintView cannot be null!");
        this.mapView.setPresenter(this);
        this.hintView.setPresenter(this);
        this.webService = webService;
        mIdlingResource.setIdleState(false);
        webService.startGame(result -> {
            game = result;
            List<HintItem> hints = result.getCurrenttarget().getHints();
            hintView.ReloadHints(hints);
            mIdlingResource.setIdleState(true);
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
        mIdlingResource.setIdleState(false);
        webService.getGame(result -> {
            game = result;
            List<HintItem> hints = game.getCurrenttarget().getHints();
            hintView.ReloadHints(hints);
            mIdlingResource.setIdleState(true);
        },game.getId());
    }


    @Override
    public void buyHint(long hintID) {
        mIdlingResource.setIdleState(false);
        webService.buyHint(result -> {
            //Reload Balance
            webService.getUser(result1 -> fetchHints());
        },game.getId(),hintID);
    }

    @Override
    public void unlockHint(long hintID) {
        mIdlingResource.setIdleState(false);
        webService.unlockHint(result -> fetchHints(),game.getId(),hintID);
    }

    public IdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
