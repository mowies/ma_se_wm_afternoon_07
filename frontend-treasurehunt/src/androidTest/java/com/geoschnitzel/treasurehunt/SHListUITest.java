package com.geoschnitzel.treasurehunt;

import com.geoschnitzel.treasurehunt.shlist.SHListActivity;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SHListUITest {
    @Rule
    public ActivityTestRule<SHListActivity> mSHListActivityTestRule =
            new ActivityTestRule<SHListActivity>(SHListActivity.class) {
            };


    @Test
    public void exampleListIsDisplayed(){
        onView(withChild(withText("München"))).check(matches(isDisplayed()));
    }
}
