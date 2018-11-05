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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.geoschnitzel.treasurehunt.utils.WaitViewAction.waitFor;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

public class MainActivityUITest {
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

        onView(withId(R.id.filter_info)).perform(click());

        onView(withId(R.id.sh_list)).perform(waitFor(isDisplayed()));

        onView(withId(R.id.filter_info)).perform(swipeFromTopToBottom());

        onView(withId(R.id.sh_list)).perform(waitFor(not(isDisplayed())));
    }

    @Test
    public void swipeFromBottom_opensBottomSheet() {
        onView(withId(R.id.filter_info)).perform(swipeFromBottomToTop());

        onView(withId(R.id.sh_list)).perform(waitFor(isDisplayed()));
    }

    @Test
    public void pressBackButton_closesBottomSheet() {
        onView(withId(R.id.filter_info)).perform(swipeFromBottomToTop());
        onView(withId(R.id.sh_list)).perform(waitFor(isDisplayed()));

        Espresso.pressBack();

        onView(withId(R.id.sh_list)).perform(waitFor(not(isDisplayed())));
    }

    @Test
    public void startNewGame()
    {
        onView(withId(R.id.filter_info)).perform(click());
        onView(withId(R.id.sh_list)).perform(waitFor(isDisplayed()));

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
            onView(withText(shitem.getName())).perform(waitFor(isDisplayed()));
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
