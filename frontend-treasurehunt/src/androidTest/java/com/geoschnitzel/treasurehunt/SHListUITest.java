package com.geoschnitzel.treasurehunt;

import android.support.test.rule.ActivityTestRule;

import com.geoschnitzel.treasurehunt.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SHListUITest {
    @Rule
    public ActivityTestRule<MainActivity> mSHListActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
            };


    @Test
    public void exampleListIsDisplayed(){
        onView(withChild(withText("München"))).check(matches(isDisplayed()));
    }
}
