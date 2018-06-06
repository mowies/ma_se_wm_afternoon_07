package com.geoschnitzel.treasurehunt.createhunt;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;
import com.geoschnitzel.treasurehunt.rest.CreateCoordinateItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.List;

public interface CreateContract {

    interface View extends IBaseView<Presenter> {
        void refreshCoordList(List<CreateCoordinateItem> items);
    }
    interface ViewHunt extends IBaseView<Presenter> {
    }
    interface ViewCoord extends IBaseView<Presenter> {
        void refreshCoordList(List<CreateCoordinateItem> items);
    }

    interface Presenter extends IBasePresenter {
        void addCoordinate(CreateCoordinateItem i);
    }

}
