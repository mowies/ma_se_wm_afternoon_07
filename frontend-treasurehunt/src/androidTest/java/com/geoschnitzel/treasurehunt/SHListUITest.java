package com.geoschnitzel.treasurehunt;

import android.support.test.rule.ActivityTestRule;

import com.geoschnitzel.treasurehunt.model.WebService;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.shlist.SHListActivity;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SHListUITest {
    @Rule
    public ActivityTestRule<SHListActivity> mSHListActivityTestRule =
            new ActivityTestRule<SHListActivity>(SHListActivity.class) {
            };


    @Test
    public void exampleListIsDisplayed() {
        List<SHListItem> shlist = WebService.instance().getSHListItems();
        for(SHListItem shitem : shlist)
            onView(withText(shitem.getName())).check(matches(isDisplayed()));
    }
}
