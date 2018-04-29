package com.geoschnitzel.treasurehunt;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.SeekBar;

import com.geoschnitzel.treasurehunt.shlist.SHListActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SHFilterUITest {
    @Rule
    public ActivityTestRule<SHListActivity> mTasksActivityTestRule =
            new ActivityTestRule<SHListActivity>(SHListActivity.class) {

            };

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
//                ((MyCustomSeekBar) view).setSelectedProgress(progress);
                ((SeekBar) view).setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set progress";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }

    @Test
    public void clickFilterButton_showsFilterDialog() {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.shfilter_author)).check(matches(isDisplayed()));
    }

    @Test
    public void dragSeekBar_changesDistanceValue() {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.shfilter_distance_to)).perform(setProgress(10));
        onView(withId(R.id.shfilter_distance_to_text)).check(matches(withText("10")));
    }

    @Test
    public void dragMinSeekbar_changesMaxSeekbar() {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.shfilter_min_rating)).perform(setProgress(3));
        onView(withId(R.id.shfilter_rating_max_text)).check(matches(withText("3")));
    }

    @Test
    public void dragMaxSeekbar_changesMinSeekbar() {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.shfilter_min_rating)).perform(setProgress(5));
        onView(withId(R.id.shfilter_max_rating)).perform(setProgress(2));
        onView(withId(R.id.shfilter_rating_min_text)).check(matches(withText("2")));
    }
}