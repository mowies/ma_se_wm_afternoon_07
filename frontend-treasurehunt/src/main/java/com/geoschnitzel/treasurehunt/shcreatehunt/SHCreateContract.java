package com.geoschnitzel.treasurehunt.shcreatehunt;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.List;

public interface SHCreateContract {

    interface View extends IBaseView<Presenter> {

    }

    interface Presenter extends IBasePresenter {
    }

}
