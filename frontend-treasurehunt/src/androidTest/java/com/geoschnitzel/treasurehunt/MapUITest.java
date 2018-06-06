package com.geoschnitzel.treasurehunt;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.geoschnitzel.treasurehunt.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MapUITest {
    @Rule
    public ActivityTestRule<MainActivity> mTasksActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
            };

    @Test
    public void clickSearch_showsMessage() {
        onView(withId(R.id.floatingSearchButton)).perform(click());
        onView(withText("Replace with your own action")).check(matches(isDisplayed()));
    }

    @Test
    public void openNavDrawer_showsNavDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open()); // Open Drawer
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
    }

    @Test
    public void clickToolbarSettings_showsNavDrawer() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
    }
}
