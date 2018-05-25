package com.geoschnitzel.treasurehunt.shlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.game.GameActivity;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import java.util.List;

public class SHListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {


    List<SHListItem> items;
    Context context;

    public SHListAdapter(List<SHListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SHListItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getHuntID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SHListItem item = items.get(i);
        View vItem = LayoutInflater.from(context).inflate(R.layout.item_shlist, null, false);
        TextView name = vItem.findViewById(R.id.shlist_item_name);
        TextView author = vItem.findViewById(R.id.shlist_item_author);
        TextView length = vItem.findViewById(R.id.shlist_item_length);
        TextView description = vItem.findViewById(R.id.shlist_item_description);
        TextView visited = vItem.findViewById(R.id.shlist_item_visited);
        RatingBar rating = vItem.findViewById(R.id.shlist_item_rating);

        name.setText(item.getName());
        author.setText(item.getAuthor());
        length.setText(Float.toString(item.getLength()));
        description.setText(item.getDescription());
        rating.setRating(item.getRating());
        if(item.getVisited())
            visited.setBackgroundColor(Color.parseColor("#4bf442"));
        else
            visited.setBackgroundColor(Color.parseColor("#ff0000"));


        return vItem;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SHListItem item = getItem(position);

        Intent game_intent = new Intent(context, GameActivity.class);
        Bundle b = new Bundle();
        b.putLong("huntID", id);
        game_intent.putExtras(b);
        context.startActivity(game_intent);
    }
}
