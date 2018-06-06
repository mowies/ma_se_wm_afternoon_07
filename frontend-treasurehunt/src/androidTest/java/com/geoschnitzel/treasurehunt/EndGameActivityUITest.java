package com.geoschnitzel.treasurehunt;

import android.support.test.rule.ActivityTestRule;

import com.geoschnitzel.treasurehunt.endgame.EndGameActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EndGameActivityUITest {

    @Rule
    public ActivityTestRule<EndGameActivity> mEndGameActivityTestRule =
            new ActivityTestRule<EndGameActivity>(EndGameActivity.class) {
            };

    @Test
    public void exampleListIsDisplayed() {
        onView(withText(R.string.congratulations)).check(matches(isDisplayed()));
    }
}
