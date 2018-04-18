package com.geoschnitzel.treasurehunt.base;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;

public interface BaseContract {

    interface View extends IBaseView<Presenter> {

    }

    interface Presenter extends IBasePresenter {

    }

}
