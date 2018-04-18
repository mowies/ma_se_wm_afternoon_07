package com.geoschnitzel.treasurehunt.shpurchase;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivity;
import com.geoschnitzel.treasurehunt.model.SHPurchaseItem;

import java.util.ArrayList;
import java.util.List;

public class SHPurchaseActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpurchase);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_send);

        GridView glshpurchase = findViewById(R.id.gvshpurchase);

        List<SHPurchaseItem> testList = new ArrayList<SHPurchaseItem>();
        String CurrenyFormat = "{0} {1}";
        testList.add(new SHPurchaseItem()
                .setCurrencyFormat(CurrenyFormat)
                .setCurrencyID(1)
                .setCurrencySymbol("$")
                .setTitle("Bronze")
                .setSHValue(20)
                .setPrice(2.99f));
        testList.add(new SHPurchaseItem(testList.get(0))
                .setTitle("Bronze")
                .setSHValue(20)
                .setPrice(4.99f));
        testList.add(new SHPurchaseItem(testList.get(0))
                .setTitle("Bronze")
                .setSHValue(100)
                .setPrice(7.99f));
        testList.add(new SHPurchaseItem(testList.get(0))
                .setTitle("Bronze")
                .setSHValue(500)
                .setPrice(12.99f));
        testList.add(new SHPurchaseItem(testList.get(0))
                .setTitle("Bronze")
                .setSHValue(1000)
                .setPrice(17.99f));
        testList.add(new SHPurchaseItem(testList.get(0))
                .setTitle("Bronze")
                .setSHValue(5000)
                .setPrice(24.99f));

        glshpurchase.setAdapter(new SHPurchaseAdapter(testList,this.getApplicationContext()));

    }

}
