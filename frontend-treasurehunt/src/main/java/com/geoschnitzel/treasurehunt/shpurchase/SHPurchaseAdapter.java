package com.geoschnitzel.treasurehunt.shpurchase;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.SHPurchaseItem;

import java.util.List;

public class SHPurchaseAdapter extends BaseAdapter {

    List<SHPurchaseItem> items;
    Context context;

    public SHPurchaseAdapter(List<SHPurchaseItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        SHPurchaseItem item = items.get(i);
        View vItem = LayoutInflater.from(context).inflate(R.layout.item_shpurchase, null, false);
        TextView tvPrice = vItem.findViewById(R.id.shpurchase_item_price);
        TextView tvSHValue = vItem.findViewById(R.id.shpurchase_item_shvalue);
        TextView tvTitle = vItem.findViewById(R.id.shpurchase_item_title);
        Button bBuy = vItem.findViewById(R.id.shpurchase_item_buy);

        tvPrice.setText(item.getPriceAsText());
        tvSHValue.setText(item.getShValueAsText());
        tvTitle.setText(item.getTitle());

        bBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return vItem;
    }
}
