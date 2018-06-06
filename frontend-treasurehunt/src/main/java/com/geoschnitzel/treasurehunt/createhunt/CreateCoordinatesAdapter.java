package com.geoschnitzel.treasurehunt.createhunt;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.CreateCoordinateItem;

import java.util.List;

public class CreateCoordinatesAdapter extends BaseAdapter {

    List<CreateCoordinateItem> items;
    Context context;

    public CreateCoordinatesAdapter(List<CreateCoordinateItem> items, Context context) {
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

        CreateCoordinateItem item = items.get(i);
        View vItem = LayoutInflater.from(context).inflate(R.layout.item_coordinate, null, false);

        TextView itemcount = vItem.findViewById(R.id.shcoordinate_item_count);
        TextView description = vItem.findViewById(R.id.shcoordinate_item_description);
        TextView lon = vItem.findViewById(R.id.shcoordinate_item_lon);
        TextView lat = vItem.findViewById(R.id.shcoordinate_item_lat);
        Button hintButton = vItem.findViewById(R.id.shcoordinate_item_hint_button);

        itemcount.setText("" + i);
        description.setText("Placeholder");
        lon.setText("" + item.getCoordinate().getLon());
        lat.setText("" + item.getCoordinate().getLat());
        hintButton.setText("0 hints");

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return vItem;
    }

    public void addItem(CreateCoordinateItem i) {
        items.add(i);
    }
}
