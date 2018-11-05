package com.geoschnitzel.treasurehunt;

import android.support.test.espresso.contrib.DrawerActions;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.createhunt.CreateActivity;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateHuntTest {
    @Rule
    public ActivityTestRule<CreateActivity> mSHCreateActivityTestRule =
            new ActivityTestRule<CreateActivity>(CreateActivity.class) {
            };

    @Test
    public void inputIsDisplayed(){
        onView(withId(R.id.new_hunt_description)).check(matches(isDisplayed()));
        onView(withId(R.id.bottombar_negative)).check(matches(isDisplayed()));
        onView(withId(R.id.bottombar_positive)).check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_image)).check(matches(isDisplayed()));
    }

    @Test
    public void insertDescription() {
        String exampleText = "TEST123\n321TEST\n";
        onView(withId(R.id.new_hunt_description))
                .perform(typeText(exampleText));
        closeSoftKeyboard();

        onView(withId(R.id.new_hunt_description))
                .check(matches(withText(exampleText)));
    }

    @Test
    public void insertName() {
        String exampleText = "NAME123\n321NAME\n";
        onView(withId(R.id.new_hunt_name))
                .perform(typeText(exampleText));
        closeSoftKeyboard();

        onView(withId(R.id.new_hunt_name))
                .check(matches(withText(exampleText)));
    }

    @Test
    public void nextButton() {
        onView(withId(R.id.bottombar_positive))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void goToSecondPage() {
        String name = "defaultName";
        String description = "defaultDescription";

        onView(withId(R.id.new_hunt_name))
                .perform(typeText(name));
        closeSoftKeyboard();

        onView(withId(R.id.new_hunt_description))
                .perform(typeText(description));
        closeSoftKeyboard();

        onView(withId(R.id.bottombar_positive))
                .perform(click());
    }

    @Test
    public void secondPageDisplayed() {
        goToSecondPage();
        onView(withId(R.id.shcoordinate_list))
                .check(matches(isDisplayed()));
        onView(withId(R.id.create_coord_add))
                .check(matches(isDisplayed()));
    }
}
