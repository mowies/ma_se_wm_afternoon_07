package com.geoschnitzel.treasurehunt.utils;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.test.espresso.IdlingResource;

import java.lang.ref.WeakReference;

public class BottomSheetStateIdlingResource implements IdlingResource {

    private static final int IDLE_POLL_DELAY_MILLIS = 100;

    private final WeakReference<BottomSheetBehavior> mBottomSheetBehavior;
    private final int mState;
    private final String mName;

    private ResourceCallback mResourceCallback;


    public BottomSheetStateIdlingResource(@NonNull BottomSheetBehavior bottomSheetBehavior, int state) {
        this.mBottomSheetBehavior = new WeakReference<BottomSheetBehavior>(bottomSheetBehavior);
        this.mState = state;
        this.mName = "State for bottom sheet behavior " + "(@" + System.identityHashCode(mBottomSheetBehavior) + ")";
    }

    @Override
    public String getName() {
        return this.mName;
    }

    @Override
    public boolean isIdleNow() {
        BottomSheetBehavior bsb = mBottomSheetBehavior.get();
        final boolean isIdle = bsb == null || bsb.getState() == mState;
        if (isIdle) {
            if (mResourceCallback != null) {
                mResourceCallback.onTransitionToIdle();
            }
        } else {
            // Force a re-check of the idle state in a little while.
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