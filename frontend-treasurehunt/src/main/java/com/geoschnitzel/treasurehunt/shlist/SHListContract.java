package com.geoschnitzel.treasurehunt.shlist;

import com.geoschnitzel.treasurehunt.IBasePresenter;
import com.geoschnitzel.treasurehunt.IBaseView;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import java.util.List;

public interface SHListContract {
    interface View extends IBaseView<Presenter> {
        void refreshSHListAdapter(List<SHListItem> items);
    }

    interface Presenter extends IBasePresenter {
        void retrieveSHListItems();
    }
}
