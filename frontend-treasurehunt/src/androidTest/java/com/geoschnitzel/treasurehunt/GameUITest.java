package com.geoschnitzel.treasurehunt;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

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

import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.not;

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

        assertTrue(game.getCurrenttarget().getHints().size() > 1);
        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);


            onView(withId(R.id.hgvhint)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkViewAction(matches(isDisplayed())))
            );
            boolean unlockingPossible = game.getCurrenttarget().getStarttime().getTime() + hint.getTimetounlockhint() * 1000 < new Date().getTime();
            boolean unlockVisible = !hint.getUnlocked();
            boolean buyVisible = !hint.getUnlocked() && !unlockingPossible;
            onView(withId(R.id.hgvhint)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkDescendantViewAction(
                                    withId(R.id.hint_item_buy_button), matches(buyVisible? isDisplayed() : not(isDisplayed()) )))
            );
            onView(withId(R.id.hgvhint)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkDescendantViewAction(
                                    withId(R.id.hint_item_unlock_button), matches(unlockVisible? isDisplayed() :  not(isDisplayed()))))
            );
        }
    }

    @Test
    public void BuyHints() {
        //Just to make sure it is waited on the resource and the game is loaded already
        onView(withId(R.id.hgvhint)).check(matches(isDisplayed()));

        Long gameID = mGameActivityTestRule.getActivity().getGameID();
        GameItem game = WebService.instance().getGame(gameID);

        assertTrue(game.getCurrenttarget().getHints().size() > 1);
        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);


            onView(withId(R.id.hgvhint)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkViewAction(matches(isDisplayed())))
            );
            boolean unlockingPossible = (game.getCurrenttarget().getStarttime().getTime() + hint.getTimetounlockhint() * 1000) < new Date().getTime();
            boolean buyVisible = !hint.getUnlocked() && !unlockingPossible;
            if(buyVisible) {
                boolean enoughMoney = WebService.instance().getUser().getBalance() > hint.getShvalue();
                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_buy_button), click()))
                );
                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_buy_button), matches(enoughMoney ? not(isDisplayed()) : isDisplayed() )))
                );
                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_unlock_button), matches(enoughMoney?  not(isDisplayed()):isDisplayed())))
                );
            }
        }
    }

    @Test
    public void UnlockHintAfterTimeout() throws InterruptedException {
        //Just to make sure it is waited on the resource and the game is loaded already
        onView(withId(R.id.hgvhint)).check(matches(isDisplayed()));

        Long gameID = mGameActivityTestRule.getActivity().getGameID();
        GameItem game = WebService.instance().getGame(gameID);

        assertTrue(game.getCurrenttarget().getHints().size() > 1);
        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);
            if(!hint.getUnlocked()) {

                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_unlock_button),matches(isDisplayed())))
                );

                Thread.sleep(hint.getTimetounlockhint() * 1000);

                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_unlock_button), click()))
                );
                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_buy_button), matches(not(isDisplayed()) )))
                );
                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_unlock_button), matches(not(isDisplayed()))))
                );
                break;
            }
        }
    }
    @Test
    public void UnlockHintBeforeTimeout() {
        //Just to make sure it is waited on the resource and the game is loaded already
        onView(withId(R.id.hgvhint)).check(matches(isDisplayed()));

        Long gameID = mGameActivityTestRule.getActivity().getGameID();
        GameItem game = WebService.instance().getGame(gameID);

        assertTrue(game.getCurrenttarget().getHints().size() > 1);
        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);

            boolean unlockingPossible = (game.getCurrenttarget().getStarttime().getTime() + hint.getTimetounlockhint() * 1000) < new Date().getTime();
            if(!hint.getUnlocked() && !unlockingPossible) {

                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_unlock_button), click()))
                );
                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_buy_button), matches(isDisplayed() )))
                );
                onView(withId(R.id.hgvhint)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_unlock_button), matches(isDisplayed())))
                );
            }
        }
    }
}
