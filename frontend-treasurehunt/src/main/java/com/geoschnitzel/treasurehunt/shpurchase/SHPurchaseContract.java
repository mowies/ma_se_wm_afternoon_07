package com.geoschnitzel.treasurehunt.shpurchase;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.List;

public interface SHPurchaseContract {

    interface View extends IBaseView<Presenter> {

    }

    interface Presenter extends IBasePresenter {
        List<SHPurchaseItem> getSHPurchaseItems();
    }

}
