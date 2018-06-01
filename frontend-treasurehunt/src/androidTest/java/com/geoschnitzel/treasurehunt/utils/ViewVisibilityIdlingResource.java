package com.geoschnitzel.treasurehunt.utils;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingResource;
import android.view.View;

import java.lang.ref.WeakReference;


public class ViewVisibilityIdlingResource implements IdlingResource {

    private static final int IDLE_POLL_DELAY_MILLIS = 100;

    private final WeakReference<View> mView;
    private final int mVisibility;
    private final String mName;

    private ResourceCallback mResourceCallback;

    public ViewVisibilityIdlingResource(@NonNull Activity activity, @IdRes int viewId, int visibility) {
        this(activity.findViewById(viewId), visibility);
    }

    public ViewVisibilityIdlingResource(@NonNull View view, int visibility) {
        mView = new WeakReference<View>(view);
        mVisibility = visibility;
        mName = "View Visibility for view " + view.getId() + "(@" + System.identityHashCode(mView) + ")";
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public boolean isIdleNow() {
        View view = mView.get();
        int visibility = view.getVisibility();
        final boolean isIdle = view == null || visibility == mVisibility;
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