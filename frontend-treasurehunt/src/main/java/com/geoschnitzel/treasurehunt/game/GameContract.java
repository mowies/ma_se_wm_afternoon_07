package com.geoschnitzel.treasurehunt.game;

import android.location.Location;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;

import java.util.List;

public interface GameContract {

    interface MapView extends IBaseView<Presenter> {
        void targetReached();
    }

    interface HintView extends IBaseView<Presenter> {
        void ReloadHints(List<HintItem> hints);
    }

    interface Presenter extends IBasePresenter {
        GameItem getCurrentGame();

        void fetchHints();

        void buyHint(long hintID);

        void unlockHint(long hintID);

        void sendUserLocation(Location mLastKnownLocation);
    }

}
