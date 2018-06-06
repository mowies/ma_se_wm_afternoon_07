package com.geoschnitzel.treasurehunt.endgame;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;

public interface EndGameContract {

    interface View extends IBaseView<EndGameContract.Presenter> {
    }

    interface Presenter extends IBasePresenter {
    }
}
