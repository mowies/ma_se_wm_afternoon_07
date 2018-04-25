package com.geoschnitzel.treasurehunt.map;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.Message;
import com.geoschnitzel.treasurehunt.rest.SearchParamItem;

import static com.google.common.base.Preconditions.checkNotNull;

public class MapPresenter implements MapContract.Presenter {
    private final MapContract.View mView;

    public MapPresenter(@NonNull MapContract.View view) {
        mView = checkNotNull(view, "tasksView cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        new HelloWorldTask().execute();
    }

    @Override
    public SearchParamItem GetSearchParams() {
        return new SearchParamItem("");
    }


    private class HelloWorldTask extends AsyncTask<Void, Void, Message> {

        @Override
        protected Message doInBackground(Void... voids) {
            return WebService.getHelloWorldMessage();
        }

        @Override
        protected void onPostExecute(Message message) {
            mView.showMessageText(String.format("Message: %s sent %s", message.getMessage(), message.getTimestamp()));
        }
    }
}
