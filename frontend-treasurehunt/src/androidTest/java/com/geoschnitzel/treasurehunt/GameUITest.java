package com.geoschnitzel.treasurehunt;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.geoschnitzel.treasurehunt.game.GameActivity;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.shlist.SHListActivity;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

public class GameUITest {
    @Rule
    public ActivityTestRule<GameActivity> mSHListActivityTestRule =
            new ActivityTestRule<GameActivity>(GameActivity.class)
            {
                @Override
                protected Intent getActivityIntent() {
                    SHListItem item = WebService.instance().getSHListItems().get(0);
                    InstrumentationRegistry.getTargetContext();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.putExtra("huntID", item.getHuntid());
                    return intent;
                }
            };


    @Test
    public void HintsAreDisplayed() {
        GameItem game = WebService.instance().getGame();

        for (int index = 0; index < game.getCurrenttarget().getHints().size(); index++) {
            HintItem hint = game.getCurrenttarget().getHints().get(index);
            onData(anything())
                    .inAdapterView(withId(R.id.hgvhint))
                    .atPosition(index)
                    .onChildView(withId(R.id.hint_item_buy_button))
                    .check(matches( hint.getUnlocked() ? not(isDisplayed()) : isDisplayed()));

            onData(anything())
                    .inAdapterView(withId(R.id.hgvhint))
                    .atPosition(index)
                    .onChildView(withId(R.id.hint_item_shvalue))
                    .check(matches( hint.getUnlocked() ? not(isDisplayed()) : isDisplayed()));

            onData(anything())
                    .inAdapterView(withId(R.id.hgvhint))
                    .atPosition(index)
                    .onChildView(withId(R.id.hint_item_unlock_button))
                    .check(matches( hint.getUnlocked() ? not(isDisplayed()) : isDisplayed()));
        }
    }
}
