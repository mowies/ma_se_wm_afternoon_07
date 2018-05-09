package com.geoschnitzel.treasurehunt;

import android.support.test.espresso.contrib.DrawerActions;
import com.geoschnitzel.treasurehunt.shcreatehunt.SHCreateActivity;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateHuntTest {
    @Rule
    public ActivityTestRule<SHCreateActivity> mSHCreateActivityTestRule =
            new ActivityTestRule<SHCreateActivity>(SHCreateActivity.class) {
            };


    @Test
    public void inputIsDisplayed(){
        onView(withId(R.id.new_hunt_description)).check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_abort)).check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_next)).check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_image)).check(matches(isDisplayed()));
    }
}
