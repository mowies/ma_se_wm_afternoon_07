package com.geoschnitzel.treasurehunt;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.rule.ActivityTestRule;

import com.geoschnitzel.treasurehunt.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

public class MainActivityUITest {
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
            };


    @Test
    public void clickOnBottomSheet_expandsBottomSheet() {
        onView(withId(R.id.filter_info)).perform(click());
        onView(withId(R.id.sh_list)).check(matches(isDisplayed()));
    }

    @Test
    public void swipeFromTop_closesBottomSheet() {
        onView(withId(R.id.filter_info)).perform(click());
        SystemClock.sleep(200);
        onView(withId(R.id.filter_info)).perform(swipeFromTopToBottom());
        SystemClock.sleep(400);
        onView(withId(R.id.sh_list)).check(matches(not(isDisplayed())));
    }

    @Test
    public void swipeFromBottom_opensBottomSheet() {
        onView(withId(R.id.filter_info)).perform(swipeFromBottomToTop());
        SystemClock.sleep(400);
        onView(withId(R.id.sh_list)).check(matches((isDisplayed())));
    }

    @Test
    public void pressBackButton_closesBottomSheet() {
        onView(withId(R.id.filter_info)).perform(swipeFromBottomToTop());
        SystemClock.sleep(200);
        Espresso.pressBack();
        SystemClock.sleep(400);
        onView(withId(R.id.sh_list)).check(matches(not(isDisplayed())));
    }

    private static ViewAction swipeFromTopToBottom() {
        return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.TOP_CENTER,
                GeneralLocation.BOTTOM_CENTER, Press.FINGER);
    }

    private static ViewAction swipeFromBottomToTop() {
        return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.BOTTOM_CENTER,
                GeneralLocation.TOP_CENTER, Press.FINGER);
    }
}
