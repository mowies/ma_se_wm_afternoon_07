package com.geoschnitzel.treasurehunt.map;

import android.location.Location;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;
import com.geoschnitzel.treasurehunt.rest.SearchParamItem;

public interface MapContract {

    interface View extends IBaseView<Presenter> {
        void openSearch(SearchParamItem sParam);

        void showMessageText(String message);
    }

    interface Presenter extends IBasePresenter {
        SearchParamItem getSearchParams();
    }

}
