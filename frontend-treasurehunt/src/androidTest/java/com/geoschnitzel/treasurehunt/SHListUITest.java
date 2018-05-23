package com.geoschnitzel.treasurehunt;

import android.support.test.rule.ActivityTestRule;

import com.geoschnitzel.treasurehunt.main.MainActivity;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SHListUITest {
    @Rule
    public ActivityTestRule<MainActivity> mSHListActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
            };


    @Test
    public void exampleListIsDisplayed() {
        onView(withId(R.id.filter_info)).perform(click());
    public void exampleListIsDisplayed() {
        List<SHListItem> shlist = WebService.instance().getSHListItems();
        for(SHListItem shitem : shlist)
            onView(withText(shitem.getName())).check(matches(isDisplayed()));
    }
}
