package com.geoschnitzel.treasurehunt.utils;

import android.os.Handler;
import android.support.test.espresso.IdlingResource;


public class BooleanCheckIdlingResource implements IdlingResource {
    private static final int IDLE_POLL_DELAY_MILLIS = 100;

    private final boolean mCheckValue;
    private final Callback mCallback;
    private final String mName;
    private ResourceCallback mResourceCallback;

    public interface Callback {
        boolean check();
    }

    public BooleanCheckIdlingResource(Callback callback, boolean expectedValue) {
        this.mCallback = callback;
        this.mCheckValue = expectedValue;
        this.mName = this.getClass().getName();
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public boolean isIdleNow() {
        final boolean result = this.mCallback.check();

        if (result == this.mCheckValue) {
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

        return result;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        mResourceCallback = resourceCallback;
    }
}