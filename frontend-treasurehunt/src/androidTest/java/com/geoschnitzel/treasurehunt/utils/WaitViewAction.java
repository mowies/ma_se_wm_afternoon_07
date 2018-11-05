package com.geoschnitzel.treasurehunt.utils;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.forkingcode.espresso.contrib.CheckAssertionAction;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.any;

public class WaitViewAction {

    private static final int interval = 100;
    private static final int timeout = 2000;

    public static ViewAction waitFor(final Matcher<View> matcher) {
        return waitFor(matcher,timeout);
    }
    public static ViewAction waitFor(final Matcher<View> matcher, final long timeout) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return any(View.class);
            }

            @Override
            public String getDescription() {
                return "wait for view to be enabled for " + timeout + " millis. ";
            }

            @Override
            public void perform(final UiController uiController, final
            View view) {
                uiController.loopMainThreadUntilIdle();


                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + timeout;
                do {
                    if (view != null && matcher.matches(view))
                        return;
                    uiController.loopMainThreadForAtLeast(interval);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }

        };
    }
}