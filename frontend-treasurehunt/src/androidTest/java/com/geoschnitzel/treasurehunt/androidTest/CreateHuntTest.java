package com.geoschnitzel.treasurehunt.androidTest;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.forkingcode.espresso.contrib.DescendantViewActions;
import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.game.GameActivity;
import com.geoschnitzel.treasurehunt.login.LoginActivity;
import com.geoschnitzel.treasurehunt.main.MainActivity;
import com.geoschnitzel.treasurehunt.map.MapFragment;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;
import com.geoschnitzel.treasurehunt.shcreatehunt.SHCreateActivity;
import com.geoschnitzel.treasurehunt.shlist.SHListFragment;
import com.geoschnitzel.treasurehunt.shpurchase.SHPurchaseActivity;
import com.geoschnitzel.treasurehunt.utils.BottomSheetStateIdlingResource;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.geoschnitzel.treasurehunt.androidTest.utils.WaitViewAction.waitFor;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateHuntTest {
    @Rule
    public ActivityTestRule<SHCreateActivity> mSHCreateActivityTestRule =
            new ActivityTestRule<SHCreateActivity>(SHCreateActivity.class) {
            };

    @Test
    public void inputIsDisplayed(){
        onView(withId(R.id.new_hunt_description)).check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_abort)).check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_next)).check(matches(isDisplayed()));
        onView(withId(R.id.new_hunt_image)).check(matches(isDisplayed()));
    }

    public static class GameUITest {

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
        public void HintsAreDisplayed() {
            //Just to make sure it is waited on the resource and the game is loaded already
            onView(withId(R.id.hint_container)).perform(waitFor(isDisplayed()));

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
        public void BuyHints() {
            //Just to make sure it is waited on the resource and the game is loaded already
            onView(withId(R.id.hint_container)).perform(waitFor(isDisplayed()));

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

                boolean buyVisible = !hintContainer.findViewHolderForAdapterPosition(index).itemView.findViewById(R.id.hint_item_unlock_button).isEnabled();

                if (buyVisible) {
                    boolean enoughMoney = WebService.instance().getUser().getBalance() > hint.getShvalue();
                    onView(withId(R.id.hint_container)).perform(
                            actionOnItemAtPosition(index,
                                    DescendantViewActions.performDescendantAction(
                                            withId(R.id.hint_item_buy_button), click()))
                    );
                    onView(withId(R.id.hint_container)).perform(
                            actionOnItemAtPosition(index,
                                    DescendantViewActions.performDescendantAction(
                                            withId(R.id.hint_item_buy_button), waitFor(enoughMoney ? not(isDisplayed()) : isDisplayed())))
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
            onView(withId(R.id.hint_container)).perform(waitFor(isDisplayed()));

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

                    onView(withId(R.id.hint_container)).perform(
                            actionOnItemAtPosition(index,
                                    DescendantViewActions.performDescendantAction(
                                            withId(R.id.hint_item_unlock_button), waitFor(isEnabled(), 10000)))
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

    public static class MainActivityUITest {
        private IdlingRegistry mIdlingRegistry = IdlingRegistry.getInstance();
        private MapFragment mMapFragment = null;
        private SHListFragment mSHListFragment = null;

        @Rule
        public ActivityTestRule<MainActivity> mMainActivityTestRule =
                new ActivityTestRule<MainActivity>(MainActivity.class) {
                };

        @Before
        public void setUp() {
            this.mMapFragment = (MapFragment) this.mMainActivityTestRule
                    .getActivity()
                    .getSupportFragmentManager()
                    .findFragmentByTag(this.getStringResource(R.string.fragment_tag_map));

            this.mSHListFragment = (SHListFragment) this.mMapFragment
                    .getChildFragmentManager()
                    .findFragmentByTag(this.getStringResource(R.string.fragment_tag_shlist));
        }

        @Test
        public void clickOnBottomSheet_expandsBottomSheet() {
            onView(withId(R.id.filter_info)).perform(click());
            onView(withId(R.id.sh_list)).check(matches(isDisplayed()));
        }

        @Test
        public void swipeFromTop_closesBottomSheet() {
            BottomSheetStateIdlingResource bssir = new BottomSheetStateIdlingResource(this.mSHListFragment.mBottomSheetBehavior, BottomSheetBehavior.STATE_EXPANDED);

            onView(withId(R.id.filter_info)).perform(click());
            this.mIdlingRegistry.register(bssir);

            onView(withId(R.id.sh_list)).check(matches(isDisplayed()));
            this.mIdlingRegistry.unregister(bssir);

            onView(withId(R.id.filter_info)).perform(swipeFromTopToBottom());

            bssir = new BottomSheetStateIdlingResource(this.mSHListFragment.mBottomSheetBehavior, BottomSheetBehavior.STATE_COLLAPSED);
            this.mIdlingRegistry.register(bssir);

            onView(withId(R.id.sh_list)).check(matches(not(isDisplayed())));
            this.mIdlingRegistry.unregister(bssir);
        }

        @Test
        public void swipeFromBottom_opensBottomSheet() {
            BottomSheetStateIdlingResource bssir = new BottomSheetStateIdlingResource(this.mSHListFragment.mBottomSheetBehavior, BottomSheetBehavior.STATE_EXPANDED);

            onView(withId(R.id.filter_info)).perform(swipeFromBottomToTop());
            this.mIdlingRegistry.register(bssir);

            onView(withId(R.id.sh_list)).check(matches(isDisplayed()));
            this.mIdlingRegistry.unregister(bssir);
        }

        @Test
        public void pressBackButton_closesBottomSheet() {
            BottomSheetStateIdlingResource bssir = new BottomSheetStateIdlingResource(this.mSHListFragment.mBottomSheetBehavior, BottomSheetBehavior.STATE_EXPANDED);

            onView(withId(R.id.filter_info)).perform(swipeFromBottomToTop());
            this.mIdlingRegistry.register(bssir);

            onView(withId(R.id.sh_list)).check(matches(isDisplayed()));
            this.mIdlingRegistry.unregister(bssir);

            bssir = new BottomSheetStateIdlingResource(this.mSHListFragment.mBottomSheetBehavior, BottomSheetBehavior.STATE_COLLAPSED);

            Espresso.pressBack();
            this.mIdlingRegistry.register(bssir);
            onView(withId(R.id.sh_list)).check(matches(not(isDisplayed())));
            this.mIdlingRegistry.unregister(bssir);
        }

        @Test
        public void startNewGame() {
            BottomSheetStateIdlingResource bssir = new BottomSheetStateIdlingResource(this.mSHListFragment.mBottomSheetBehavior, BottomSheetBehavior.STATE_EXPANDED);

            onView(withId(R.id.filter_info)).perform(click());
            this.mIdlingRegistry.register(bssir);

            onView(withId(R.id.sh_list)).check(matches(isDisplayed()));
            this.mIdlingRegistry.unregister(bssir);

            onData(anything())
                    .inAdapterView(withId(R.id.sh_list))
                    .atPosition(0)
                    .perform(click());
        }


        @Test
        public void exampleListIsDisplayed() {
            onView(withId(R.id.filter_info)).perform(click());
            List<SHListItem> shlist = WebService.instance().getSHListItems();
            for (SHListItem shitem : shlist)
                onView(withText(shitem.getName())).check(matches(isDisplayed()));
        }

        private static ViewAction swipeFromTopToBottom() {
            return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.TOP_CENTER,
                    GeneralLocation.BOTTOM_CENTER, Press.FINGER);
        }

        private static ViewAction swipeFromBottomToTop() {
            return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.BOTTOM_CENTER,
                    GeneralLocation.TOP_CENTER, Press.FINGER);
        }

        private String getStringResource(int id) {
            return this.mMainActivityTestRule.getActivity().getString(id);
        }
    }

    @RunWith(AndroidJUnit4.class)
    @LargeTest
    public static class MapUITest {
        @Rule
        public ActivityTestRule<MainActivity> mTasksActivityTestRule =
                new ActivityTestRule<MainActivity>(MainActivity.class) {

                };

        @Test
        public void clickSearch_showsMessage() {
            onView(withId(R.id.floatingSearchButton)).perform(click());
            onView(withText("Replace with your own action")).check(matches(isDisplayed()));
        }

        @Test
        public void openNavDrawer_showsNavDrawer() {
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open()); // Open Drawer
            onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        }

        @Test
        public void clickToolbarSettings_showsNavDrawer() {
            onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
            onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        }
    }

    @RunWith(AndroidJUnit4.class)
    @LargeTest
    public static class SHFilterUITest {
        @Rule
        public ActivityTestRule<MainActivity> mTasksActivityTestRule =
                new ActivityTestRule<MainActivity>(MainActivity.class) {

                };

        public static ViewAction setProgress(final int progress) {
            return new ViewAction() {
                @Override
                public void perform(UiController uiController, View view) {
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
            onView(withId(R.id.shfilter_distance_to_text)).check(matches(withText("10 km")));
        }

        @Test
        public void dragMinRatingSeekbar_changesMaxSeekbar() {
            onView(withId(R.id.action_filter)).perform(click());
            onView(withId(R.id.shfilter_max_rating)).perform(setProgress(1));
            onView(withId(R.id.shfilter_min_rating)).perform(setProgress(3));
            onView(withId(R.id.shfilter_rating_max_text)).check(matches(withText("4")));
        }

        @Test
        public void dragMaxRatingSeekbar_changesMinSeekbar() {
            onView(withId(R.id.action_filter)).perform(click());
            onView(withId(R.id.shfilter_min_rating)).perform(setProgress(5));
            onView(withId(R.id.shfilter_max_rating)).perform(setProgress(2));
            onView(withId(R.id.shfilter_rating_min_text)).check(matches(withText("3")));
        }

        @Test
        public void dragMinSHLengthSeekbar_changesMaxSeekbar() {
            onView(withId(R.id.action_filter)).perform(click());
            onView(withId(R.id.shfilter_length_hunt_max)).perform(setProgress(1));
            onView(withId(R.id.shfilter_length_hunt_min)).perform(setProgress(20));
            onView(withId(R.id.shfilter_length_hunt_max_text)).check(matches(withText("20 km")));
        }

        @Test
        public void dragMaxSHLengthSeekbar_changesMinSeekbar() {
            onView(withId(R.id.action_filter)).perform(click());
            onView(withId(R.id.shfilter_length_hunt_min)).perform(setProgress(50));
            onView(withId(R.id.shfilter_length_hunt_max)).perform(setProgress(20));
            onView(withId(R.id.shfilter_length_hunt_min_text)).check(matches(withText("20 km")));
        }

        @Test
        public void pressResetButton_resetsFilterOptions() {
            onView(withId(R.id.action_filter)).perform(click());

            onView(withId(R.id.shfilter_distance_to)).perform(setProgress(10));
            onView(withId(R.id.shfilter_max_rating)).perform(setProgress(1));
            onView(withId(R.id.shfilter_min_rating)).perform(setProgress(3));

            onView(withId(android.R.id.button3)).perform(click());

            onView(withId(R.id.shfilter_rating_min_text)).check(matches(withText("1")));
            onView(withId(R.id.shfilter_rating_max_text)).check(matches(withText("5")));
            onView(withId(R.id.shfilter_distance_to_text)).check(matches(withText("100 km")));
            onView(withId(R.id.shfilter_length_hunt_max_text)).check(matches(withText("100 km")));
            onView(withId(R.id.shfilter_length_hunt_min_text)).check(matches(withText("0 km")));
            onView(withId(R.id.shfilter_name)).check(matches(withText("")));
            onView(withId(R.id.shfilter_author)).check(matches(withText("")));
        }
    }

    @RunWith(AndroidJUnit4.class)
    @LargeTest
    public static class SHLoginTest {

        @Rule
        public ActivityTestRule<LoginActivity> mTasksActivityTestRule =
                new ActivityTestRule<LoginActivity>(LoginActivity.class) {
                };

        @Test
        public void clickLoginButtonIsShown() {
            onView(withId(R.id.sign_in_button)).check(matches(isDisplayed()));
        }

    }

    @RunWith(AndroidJUnit4.class)
    @LargeTest
    public static class SHPurchaseUITest {

        private WebService webService;

        @Before
        public void setup() {
            webService = WebService.instance();
        }

        @Rule
        public ActivityTestRule<SHPurchaseActivity> mTasksActivityTestRule =
                new ActivityTestRule<SHPurchaseActivity>(SHPurchaseActivity.class) {
                };

        @Test
        public void clickBuyButton_ShowsMessage() {
            webService.getSHPurchaseItems(dataList -> {
                for (int index = 0; index < dataList.size(); index++) {
                    onData(anything())
                            .inAdapterView(withId(R.id.shpurchase_gvitems))
                            .atPosition(index)
                            .onChildView(withId(R.id.shpurchase_item_buy))
                            .perform(click());
                }

            });
        }

        @Test
        public void LoadData_CheckVisible() {
            webService.getSHPurchaseItems(dataList -> {
                for (int index = 0; index < dataList.size(); index++) {
                    SHPurchaseItem data = dataList.get(index);
                    onData(anything())
                            .inAdapterView(withId(R.id.shpurchase_gvitems))
                            .atPosition(index)
                            .onChildView(withText(data.getTitle()))
                            .check(matches(isDisplayed()));

                    onData(anything())
                            .inAdapterView(withId(R.id.shpurchase_gvitems))
                            .atPosition(index)
                            .onChildView(withText(data.getShValueAsText()))
                            .check(matches(isDisplayed()));

                    onData(anything())
                            .inAdapterView(withId(R.id.shpurchase_gvitems))
                            .atPosition(index)
                            .onChildView(withText(data.getPriceAsText()))
                            .check(matches(isDisplayed()));
                }
            });
        }

    }
}
