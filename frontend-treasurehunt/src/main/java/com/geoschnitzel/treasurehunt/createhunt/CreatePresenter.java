package com.geoschnitzel.treasurehunt.createhunt;

import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.CreateCoordinateItem;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CreatePresenter implements CreateContract.Presenter {
    private final CreateContract.View mView;


    private WebService webService;

    private List<CreateCoordinateItem> coordinates = null;

    public CreatePresenter(@NonNull CreateContract.View view, WebService webService) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);

        this.webService = webService;
        coordinates = new ArrayList<>();
    }

    @Override
    public void addCoordinate(CreateCoordinateItem i) {
        coordinates.add(i);
        mView.refreshCoordList(coordinates);
    }

    @Override
    public void start() {

    }
}
