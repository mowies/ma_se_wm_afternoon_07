package com.geoschnitzel.treasurehunt.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.createhunt.CreateActivity;
import com.geoschnitzel.treasurehunt.login.LoginActivity;
import com.geoschnitzel.treasurehunt.shpurchase.SHPurchaseActivity;

public abstract class BaseActivityNavigationDrawer extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_with_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView nav_view = (NavigationView) mDrawerLayout.findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_new:
                Intent new_intent = new Intent(this, CreateActivity.class);
                startActivity(new_intent);
                break;
            case R.id.nav_logout:
                Intent login_intent = new Intent(this, LoginActivity.class);
                startActivity(login_intent);
                break;
            case R.id.nav_buy:
                Intent buy_intent = new Intent(this, SHPurchaseActivity.class);
                startActivity(buy_intent);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}