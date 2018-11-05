package com.geoschnitzel.treasurehunt;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.util.Log;

import com.forkingcode.espresso.contrib.DescendantViewActions;
import com.geoschnitzel.treasurehunt.game.GameActivity;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.geoschnitzel.treasurehunt.utils.WaitViewAction.waitFor;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.not;

public class GameUITest {

    @Rule
    public final ActivityTestRule<GameActivity> mGameActivityTestRule =
            new ActivityTestRule<GameActivity>(GameActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    SHListItem hunt = WebService.instance().getSHListItems().get(0);
                    InstrumentationRegistry.getTargetContext();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.putExtra("huntID", hunt.getHuntid());
                    return intent;
                }
            };

    private String getStringResource(int id) {
        return this.mGameActivityTestRule.getActivity().getString(id);
    }

    @Test
    public void GameReachedPosition() {
        onView(withId(R.id.hint_container)).perform(waitFor(isDisplayed()));
        GameItem gameItem = mGameActivityTestRule.getActivity().getGame();
        HorizontalGridView hintContainer = this.mGameActivityTestRule.getActivity().findViewById(R.id.hint_container);

    }

    @Test
    public void HintsAreDisplayed() {
        //Just to make sure it is waited on the resource and the game is loaded already
        onView(withId(R.id.hint_container)).perform(waitFor(isDisplayed()));

        GameItem game = mGameActivityTestRule.getActivity().getGame();
        HorizontalGridView hintContainer = this.mGameActivityTestRule.getActivity().findViewById(R.id.hint_container);

        assertTrue(game.getCurrenttarget().getHints().size() > 1);

        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            Log.d("TreasureHunt", "index: " + index);

            HintItem hint = game.getCurrenttarget().getHints().get(index);

            onView(withId(R.id.hint_container)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkViewAction(matches(isDisplayed())))
            );

            boolean unlockVisible = !hint.getUnlocked();
            boolean buyVisible = !hint.getUnlocked() && !hintContainer.findViewHolderForAdapterPosition(index).itemView.findViewById(R.id.hint_item_unlock_button).isEnabled();

            onView(withId(R.id.hint_container)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkDescendantViewAction(
                                    withId(R.id.hint_item_buy_button), matches(buyVisible ? isDisplayed() : not(isDisplayed()))))
            );

            onView(withId(R.id.hint_container)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkDescendantViewAction(
                                    withId(R.id.hint_item_unlock_button), matches(unlockVisible ? isDisplayed() : not(isDisplayed()))))
            );
        }
    }


    @Test
    public void UnlockHintAfterTimeout() throws InterruptedException {
        onView(withId(R.id.hint_container)).perform(waitFor(isDisplayed()));


        GameItem game = mGameActivityTestRule.getActivity().getGame();

        assertTrue(game.getCurrenttarget().getHints().size() > 1);
        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);
            if (!hint.getUnlocked()) {

                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_unlock_button), matches(isDisplayed())))
                );

                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_unlock_button), waitFor(isEnabled(),10000)))
                );

                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_unlock_button), click()))
                );

                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_buy_button), waitFor(not(isDisplayed()))))
                );

                onView(withId(R.id.hint_container)).perform(
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
        onView(withId(R.id.hint_container)).perform(waitFor(isDisplayed()));


        GameItem game = mGameActivityTestRule.getActivity().getGame();

        assertTrue(game.getCurrenttarget().getHints().size() > 1);
        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);

            boolean unlockingPossible = (game.getCurrenttarget().getStarttime().getTime() + hint.getTimetounlockhint() * 1000) < new Date().getTime();
            if (!hint.getUnlocked() && !unlockingPossible) {

                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_unlock_button), click()))
                );

                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_buy_button), waitFor(isDisplayed())))
                );
                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_unlock_button), matches(isDisplayed())))
                );
            }
        }
    }
}
