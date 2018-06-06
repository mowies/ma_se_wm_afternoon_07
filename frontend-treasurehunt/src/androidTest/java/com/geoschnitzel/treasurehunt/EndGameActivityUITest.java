package com.geoschnitzel.treasurehunt;

import android.support.test.espresso.Espresso;
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
    public void showView_showsFinishView() {
        onView(withText(R.string.end_game_congratulations)).check(matches(isDisplayed()));
        onView(withText(R.string.end_game_finish_talk)).check(matches(isDisplayed()));
    }

    @Test
    public void pressBack_opensMainActivity() {
        Espresso.pressBack();
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
    }
}
