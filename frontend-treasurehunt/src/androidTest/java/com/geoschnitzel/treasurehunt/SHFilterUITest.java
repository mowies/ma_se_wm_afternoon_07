package com.geoschnitzel.treasurehunt;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.geoschnitzel.treasurehunt.main.SHListActivity;
import com.geoschnitzel.treasurehunt.shlist.SHListActivity;

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
public class SHFilterUITest {
    @Rule
    public ActivityTestRule<SHListActivity> mTasksActivityTestRule =
            new ActivityTestRule<SHListActivity>(SHListActivity.class) {

            };

    @Test
    public void clickFilterButton_showsFilterDialog(){
        onView(withId(R.id.filter_button).perform(click()));
        onView(withId(R.id.filter_layout).check(matches(isDisplayed())));
    }


}