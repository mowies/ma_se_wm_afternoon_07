package com.geoschnitzel.treasurehunt;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;
import com.geoschnitzel.treasurehunt.shpurchase.SHPurchaseActivity;
import com.geoschnitzel.treasurehunt.shpurchase.SHPurchaseContract;
import com.geoschnitzel.treasurehunt.shpurchase.SHPurchasePresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SHPurchaseUITest {

    SHPurchaseContract.Presenter mPresenter;

    @Rule
    public ActivityTestRule<SHPurchaseActivity> mTasksActivityTestRule =
            new ActivityTestRule<SHPurchaseActivity>(SHPurchaseActivity.class) {
            };

    @Before
    public void LoadTestData() {
        mPresenter = new SHPurchasePresenter(mTasksActivityTestRule.getActivity());
    }

    @Test
    public void clickBuyButton_ShowsMessage() {
        List<SHPurchaseItem> dataList = mPresenter.getSHPurchaseItems();
        for (int index = 0; index < dataList.size(); index++) {
            onData(anything())
                    .inAdapterView(withId(R.id.shpurchase_gvitems))
                    .atPosition(index)
                    .onChildView(withId(R.id.shpurchase_item_buy))
                    .perform(click());
            onView(withText("Replace with your own action")).check(matches(isDisplayed()));
        }
    }

    @Test
    public void LoadData_CheckVisible() {
        List<SHPurchaseItem> dataList = mPresenter.getSHPurchaseItems();
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
    }

}