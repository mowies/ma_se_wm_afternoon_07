package com.geoschnitzel.treasurehunt;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.geoschnitzel.treasurehunt.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SHLoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> mTasksActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class) {
            };

    @Test
    public void clickLoginButtonIsShown() {
        onView(withId(R.id.sign_in_button)).check(matches(isDisplayed()));
    }

}