package com.geoschnitzel.treasurehunt;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.util.Log;
import android.view.View;

import com.forkingcode.espresso.contrib.DescendantViewActions;
import com.geoschnitzel.treasurehunt.game.GameActivity;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.utils.BooleanCheckIdlingResource;
import com.geoschnitzel.treasurehunt.utils.ViewVisibilityIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.not;

public class GameUITest {
    private IdlingRegistry mIdlingRegistry = IdlingRegistry.getInstance();
    private BooleanCheckIdlingResource bcir;

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

    @Before
    public void registerIdlingResource() {
        this.bcir = new BooleanCheckIdlingResource(
                new BooleanCheckIdlingResource.Callback() {
                    @Override
                    public boolean check() {
                        HorizontalGridView view = mGameActivityTestRule.getActivity().findViewById(R.id.hint_container);
                        return view.getAdapter() != null && view.getAdapter().getItemCount() > 0;
                    }
                }, true);
        this.mIdlingRegistry.register(this.bcir);
        onView(withId(R.id.hint_container)).check(matches(isDisplayed()));
        this.mIdlingRegistry.unregister(this.bcir);
    }

    @Test
    public void HintsAreDisplayed() {
        //Just to make sure it is waited on the resource and the game is loaded already
        onView(withId(R.id.hint_container)).check(matches(isDisplayed()));

        Long gameID = mGameActivityTestRule.getActivity().getGameID();
        GameItem game = WebService.instance().getGame(gameID);
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
            boolean buyVisible = !hintContainer.getChildAt(index).findViewById(R.id.hint_item_unlock_button).isEnabled();

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
    public void BuyHints() {
        //Just to make sure it is waited on the resource and the game is loaded already
        onView(withId(R.id.hint_container)).check(matches(isDisplayed()));

        Long gameID = mGameActivityTestRule.getActivity().getGameID();
        GameItem game = WebService.instance().getGame(gameID);
        HorizontalGridView hintContainer = this.mGameActivityTestRule.getActivity().findViewById(R.id.hint_container);

        assertTrue(game.getCurrenttarget().getHints().size() > 1);
        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);


            onView(withId(R.id.hint_container)).perform(
                    actionOnItemAtPosition(index,
                            DescendantViewActions.checkViewAction(matches(isDisplayed())))
            );

            boolean buyVisible = !hintContainer.getChildAt(index).findViewById(R.id.hint_item_unlock_button).isEnabled();

            if (buyVisible) {
                boolean enoughMoney = WebService.instance().getUser().getBalance() > hint.getShvalue();
                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_buy_button), click()))
                );
                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_buy_button), matches(enoughMoney ? not(isDisplayed()) : isDisplayed())))
                );
                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_unlock_button), matches(enoughMoney ? not(isDisplayed()) : isDisplayed())))
                );
            }
        }
    }

    @Test
    public void UnlockHintAfterTimeout() throws InterruptedException {

        Long gameID = mGameActivityTestRule.getActivity().getGameID();
        GameItem game = WebService.instance().getGame(gameID);

        assertTrue(game.getCurrenttarget().getHints().size() > 1);
        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);
            if (!hint.getUnlocked()) {

                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_unlock_button), matches(isDisplayed())))
                );

                HorizontalGridView hintContainer = this.mGameActivityTestRule.getActivity().findViewById(R.id.hint_container);
                final View unlockButton = hintContainer.findViewHolderForAdapterPosition(index).itemView.findViewById(R.id.hint_item_unlock_button);
                BooleanCheckIdlingResource checkEnabled = new BooleanCheckIdlingResource(
                        new BooleanCheckIdlingResource.Callback() {
                            @Override
                            public boolean check() {
                                return unlockButton.isEnabled();
                            }
                        }, true);

                this.mIdlingRegistry.register(checkEnabled);
                onView(withId(R.id.hint_container)).check(matches(isDisplayed()));
                this.mIdlingRegistry.unregister(checkEnabled);

                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.performDescendantAction(
                                        withId(R.id.hint_item_unlock_button), click()))
                );

                final View buttonContainer = hintContainer.findViewHolderForAdapterPosition(index).itemView.findViewById(R.id.hint_item_buy);
                ViewVisibilityIdlingResource vvir = new ViewVisibilityIdlingResource(buttonContainer, View.GONE);
                this.mIdlingRegistry.register(vvir);
                onView(withId(R.id.hint_container)).check(matches(isDisplayed()));
                this.mIdlingRegistry.unregister(vvir);

                onView(withId(R.id.hint_container)).perform(
                        actionOnItemAtPosition(index,
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_buy_button), matches(not(isDisplayed()))))
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
        onView(withId(R.id.hint_container)).check(matches(isDisplayed()));

        Long gameID = mGameActivityTestRule.getActivity().getGameID();
        GameItem game = WebService.instance().getGame(gameID);

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
                                DescendantViewActions.checkDescendantViewAction(
                                        withId(R.id.hint_item_buy_button), matches(isDisplayed())))
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
