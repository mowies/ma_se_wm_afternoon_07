package com.geoschnitzel.treasurehunt.game;

import android.location.Location;
import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.GameTargetItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.HuntItem;
import com.geoschnitzel.treasurehunt.rest.TargetItem;
import com.geoschnitzel.treasurehunt.utils.CalDistance;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GamePresenter implements GameContract.Presenter {
    private GameContract.MapView mapView = null;
    private GameContract.HintView hintView = null;
    private GameItem game = null;
    private HuntItem hunt = null;
    private WebService webService = null;


    public GamePresenter(@NonNull GameContract.MapView mapView, GameContract.HintView hintView, long huntID,WebService webService) {
        this.mapView = checkNotNull(mapView, "mapView cannot be null!");
        this.hintView = checkNotNull(hintView, "hintView cannot be null!");
        this.mapView.setPresenter(this);
        this.hintView.setPresenter(this);
        this.webService = webService;
        webService.getHunt(result -> {
            hunt = result;

            game = new GameItem(new ArrayList<>(),new Date(),null);

            NextTarget(hunt.getTargets().get(0));

            fetchHints();
        },huntID);
    }
    private void NextTarget(TargetItem nextTarget)
    {

        if(nextTarget != null) {
            List<HintItem> hints = new ArrayList<>();
            for(HintItem hint : nextTarget.getHints()) {
                hint.setUnlocked(hint == nextTarget.getHints().get(0));
                hints.add(hint);
            }
            game.getTargets().add(new GameTargetItem(nextTarget.getId(),new Date(),null,hints,nextTarget.getArea()));
        }
        else
        {
            game.setEndtime(new Date());
        }

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
        hintView.ReloadHints(game.getCurrenttarget().getHints());
    }


    @Override
    public void buyHint(long hintID) {
        TargetItem currentTarget = hunt.getTargets().get(game.getTargets().size()-1);
        HintItem buyHint = null;
        for(HintItem hint : currentTarget.getHints())
        {
            if(hint.getId() == hintID) {
                buyHint = hint;
                break;
            }
        }

        buyHint.setUnlocked(true);
        fetchHints();
    }

    @Override
    public void unlockHint(long hintID) {

        GameTargetItem currentGameTarget = game.getCurrenttarget();
        HintItem unlockHint = null;
        for(HintItem hint : currentGameTarget.getHints())
        {
            if(hint.getId() == hintID) {
                unlockHint = hint;
                break;
            }
        }

        if((currentGameTarget.getStarttime().getTime()  + unlockHint.getTimetounlockhint() * 1000) > new Date().getTime())
            return;

        unlockHint.setUnlocked(true);
    }

    @Override
    public void sendUserLocation(Location mLastKnownLocation) {

        if( CalDistance.distance(game.getCurrenttarget(),mLastKnownLocation, CalDistance.ScaleType.Meter) <
                (double)game.getCurrenttarget().getArea().getRadius())
        {
            game.getCurrenttarget().setEndtime(new Date());

            TargetItem nextTarget = null;
            for (TargetItem target : hunt.getTargets()) {
                boolean contains = false;
                for(GameTargetItem gameTarget : game.getTargets()) {
                    if(gameTarget.getId()== target.getId()) {
                        contains = true;
                        break;
                    }
                }
                if (!contains)
                    nextTarget = target;
            }

            NextTarget(nextTarget);

            fetchHints();
        }
    }
}
