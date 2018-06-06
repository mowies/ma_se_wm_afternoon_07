package com.geoschnitzel.treasurehunt;

import android.support.design.widget.BottomSheetBehavior;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.rule.ActivityTestRule;

import com.geoschnitzel.treasurehunt.main.MainActivity;
import com.geoschnitzel.treasurehunt.map.MapFragment;
import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.shlist.SHListFragment;
import com.geoschnitzel.treasurehunt.utils.BottomSheetStateIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

public class MainActivityUITest {
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
    public void startNewGame()
    {
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
