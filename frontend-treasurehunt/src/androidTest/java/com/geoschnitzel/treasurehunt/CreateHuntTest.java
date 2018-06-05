package com.geoschnitzel.treasurehunt;

import com.geoschnitzel.treasurehunt.createhunt.CreateActivity;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;


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
        onView(withId(R.id.new_hunt_abort)).check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_next)).check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_image)).check(matches(isDisplayed()));
    }

    @Test
    public void insertDescription() {
        String exampleText = "TEST123\n321TEST\n";
        onView(withId(R.id.new_hunt_description))
                .perform(click())
                .perform(typeText(exampleText));
        closeSoftKeyboard();

        onView(withId(R.id.new_hunt_description))
                .check(matches(withText(exampleText)));
    }

    @Test
    public void insertName() {
        String exampleText = "NAME123\n321NAME\n";
        onView(withId(R.id.new_hunt_name))
                .perform(click())
                .perform(typeText(exampleText));
        closeSoftKeyboard();

        onView(withId(R.id.new_hunt_name))
                .check(matches(withText(exampleText)));
    }

    @Test
    public void nextButton() {
        onView(withId(R.id.new_hunt_next))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Test
    public void abortButton() {
        //click abort button 1 time
        onView(withId(R.id.new_hunt_abort))
                .check(matches(isDisplayed()))
                .perform(click());
        //check if abort warning is shown
        onView(withId(R.id.new_hunt_abort_message))
                .check(matches(isDisplayed()));
        //press abort button second time
        onView(withId(R.id.new_hunt_abort))
                .check(matches(isDisplayed()))
                .perform(click());
        //check if left activity
        onView(withId(R.id.new_hunt_abort))
                .check(matches(not(isDisplayed())));
    }

    public void goToSecondPage() {
        String name = "defaultName";
        String description = "defaultDescription";

        onView(withId(R.id.new_hunt_name))
                .perform(click())
                .perform(typeText(name));
        closeSoftKeyboard();

        onView(withId(R.id.new_hunt_description))
                .perform(click())
                .perform(typeText(description));
        closeSoftKeyboard();

        onView(withId(R.id.new_hunt_next))
                .perform(click());
    }

    @Test
    public void secondPageDisplayed() {
        goToSecondPage();
        onView(withId(R.id.new_hunt_coord_list))
                .check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_add_coord))
                .check(matches(isDisplayed()));
    }

    @Test
    public void addCoordinate() {
        goToSecondPage();
        onView(withId(R.id.new_hunt_coord_list))
                .check(hasChildCount(0));
        onView(withId(R.id.new_hunt_add_coord))
                .perform(click());
        onView(withId(R.id.new_hunt_coord_selector))
                .check(matches(isDisplayed()));
        //TODO select coordinate
        onView(withId(R.id.new_hunt_add_coord))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withId(R.id.new_hunt_coord_list))
                .check(hasChildCount(1));
    }

    @Test
    public void abortAddCoordinate() {
        goToSecondPage();
        onView(withId(R.id.new_hunt_coord_list))
                .check(hasChildCount(0));
        //add new coordinate
        onView(withId(R.id.new_hunt_add_coord))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withId(R.id.new_hunt_coord_selector))
                .check(matches(isDisplayed()));

        //abort the creation
        onView(withId(R.id.new_hunt_coord_abort))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.new_hunt_coord_selector))
                .check(matches(not(isDisplayed())));

        //assert that no coordinate got added
        onView(withId(R.id.new_hunt_coord_list))
                .check(hasChildCount(0));
    }

    @Test
    public void addCurrentLocation() {
        goToSecondPage();
        onView(withId(R.id.new_hunt_coord_list))
                .check(hasChildCount(0));
        //add new coordinate
        onView(withId(R.id.new_hunt_add_coord))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withId(R.id.new_hunt_coord_selector))
                .check(matches(isDisplayed()));
        //select current location
        onView(withId(R.id.new_hunt_coord_current))
                .check(matches(isDisplayed()))
                .perform(click());

        //assert that no coordinate got added
        onView(withId(R.id.new_hunt_coord_list))
                .check(matches(isDisplayed()))
                .check(hasChildCount(1));
    }

    @Test
    public void addGame() {
        goToSecondPage();
        addCurrentLocation();
        onView(withId(R.id.new_hunt_next))
                .check(matches(isDisplayed()))
                .perform(click());
    }
}
