package com.geoschnitzel.treasurehunt.shpurchase;

import android.os.Bundle;
import android.widget.GridView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class SHPurchaseActivity extends BaseActivity implements SHPurchaseContract.View {

    SHPurchaseContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpurchase);

        mPresenter = new SHPurchasePresenter(this);

        GridView glshpurchase = findViewById(R.id.shpurchase_gvitems);
        glshpurchase.setAdapter(new SHPurchaseAdapter(mPresenter.getSHPurchaseItems(), this.getApplicationContext()));

    }

    @Override
    public void setPresenter(SHPurchaseContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
