package com.geoschnitzel.treasurehunt.utils;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingResource;
import android.view.View;

import java.lang.ref.WeakReference;


public class ViewEnabledIdlingResource implements IdlingResource {

    private static final int IDLE_POLL_DELAY_MILLIS = 100;

    private final WeakReference<View> mView;
    private final boolean mEnabled;
    private final String mName;

    private ResourceCallback mResourceCallback;

    public ViewEnabledIdlingResource(@NonNull Activity activity, @IdRes int viewId, boolean enabled) {
        this(activity.findViewById(viewId), enabled);
    }


    public ViewEnabledIdlingResource(@NonNull View view, boolean enabled) {
        mView = new WeakReference<View>(view);
        mEnabled = enabled;
        mName = "View Visibility for view " + view.getId() + "(@" + System.identityHashCode(mView) + ")";
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public boolean isIdleNow() {
        View view = mView.get();
        final boolean isIdle = view == null || view.isEnabled() == this.mEnabled;
        if (isIdle) {
            if (mResourceCallback != null) {
                mResourceCallback.onTransitionToIdle();
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isIdleNow();
                }
            }, IDLE_POLL_DELAY_MILLIS);
        }

        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        mResourceCallback = resourceCallback;
    }
}