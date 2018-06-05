package com.geoschnitzel.treasurehunt.createhunt;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;

public interface CreateContract {

    interface View extends IBaseView<Presenter> {

    }

    interface Presenter extends IBasePresenter {
    }

}
