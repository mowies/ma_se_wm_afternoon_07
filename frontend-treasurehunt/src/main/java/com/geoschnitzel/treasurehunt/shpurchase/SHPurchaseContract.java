package com.geoschnitzel.treasurehunt.shpurchase;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.List;

public interface SHPurchaseContract {

    interface View extends IBaseView<Presenter> {

    }

    interface Presenter extends IBasePresenter {
        void retrieveSHPurchaseItems(WebService.WebServiceCallback<List<SHPurchaseItem>> callback);
    }

}
