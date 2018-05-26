package com.geoschnitzel.treasurehunt;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.web.sugar.Web;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.forkingcode.espresso.contrib.DescendantViewActions;
import com.geoschnitzel.treasurehunt.game.GameActivity;
import com.geoschnitzel.treasurehunt.game.GameHintViewFragment;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class GameUITest {
    private IdlingRegistry mIdlingRegistry = IdlingRegistry.getInstance();
    private SHListItem hunt = null;
    private GameHintViewFragment hintViewFragment;

    @Rule
    public ActivityTestRule<GameActivity> mGameActivityTestRule =
            new ActivityTestRule<GameActivity>(GameActivity.class)
            {
                @Override
                protected Intent getActivityIntent() {
                    hunt = WebService.instance().getSHListItems().get(0);
                    InstrumentationRegistry.getTargetContext();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.putExtra("huntID", hunt.getHuntid());
                    return intent;
                }
            };

    private String getStringResource(int id) {
        return this.mGameActivityTestRule.getActivity().getString(id);
    }

    IdlingResource mIdlingResource;
    @Before
    public void registerIdlingResource() {
        mIdlingResource = this.mGameActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        this.mIdlingRegistry.register(mIdlingResource);
    }
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            this.mIdlingRegistry.unregister(mIdlingResource);
        }
    }
    @Test
    public void HintsAreDisplayed() {
        //Just to make sure it is waited on the resource and the game is loaded already
        onView(withId(R.id.hgvhint)).check(matches(isDisplayed()));

        Long gameID = mGameActivityTestRule.getActivity().getGameID();
        GameItem game = WebService.instance().getGame(gameID);

        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);


            onView(withId(R.id.hgvhint)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkViewAction(matches(isDisplayed())))
            );
            onView(withId(R.id.hgvhint)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkDescendantViewAction(
                                    withId(R.id.hint_item_buy_button), matches(isDisplayed())))
            );
        }
    }
}
