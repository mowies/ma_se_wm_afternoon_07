package com.geoschnitzel.treasurehunt.game;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameHintAdapter extends RecyclerView.Adapter<GameHintAdapter.ViewHolder> {

    GameContract.Presenter mPresenter;
    List<HintItem> items;
    Context context;

    public GameHintAdapter(Context context, GameContract.Presenter mPresenter) {
        this.items = new ArrayList<>();
        this.context = context;
        this.mPresenter = mPresenter;
    }
    public void updateItems(List<HintItem> items)
    {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public GameHintAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vItem = LayoutInflater.from(context).inflate(R.layout.item_hint, null, false);
        return new GameHintAdapter.ViewHolder(vItem);
    }

    @Override
    public void onBindViewHolder(GameHintAdapter.ViewHolder holder, int position) {
        GameItem game = mPresenter.getCurrentGame();
        final HintItem item = items.get(position);
        holder.hintID = item.getId();
        if (item.getUnlocked()) {
            switch (item.getType()) {
                case IMAGE:
                    holder.image.setImageResource(android.R.drawable.alert_dark_frame);
                    holder.image.setVisibility(View.VISIBLE);
                    break;
                case TEXT:
                    holder.description.setText(item.getDescription());
                    holder.description.setVisibility(View.VISIBLE);
                    break;
                case COORDINATE:
                    break;
                case DIRECTION:
                    break;
            }
            holder.lbuy.setVisibility(View.GONE);
            holder.lshow.setVisibility(View.VISIBLE);
        } else {
            Drawable icon = null;
            switch (item.getType()) {
                case IMAGE:
                    icon = context.getResources().getDrawable(android.R.drawable.alert_dark_frame);
                    break;
                case TEXT:
                    icon = context.getResources().getDrawable(android.R.drawable.alert_dark_frame);
                    break;
                case COORDINATE:
                    icon = context.getResources().getDrawable(android.R.drawable.alert_dark_frame);
                    break;
                case DIRECTION:
                    icon = context.getResources().getDrawable(android.R.drawable.alert_dark_frame);
                    break;
            }
            holder.buy_icon.setImageDrawable(icon);

            holder.unlock_chrono.setBase(game.getCurrenttarget().getStarttime().getTime() + item.getTimetounlockhint() * 1000);
            holder.unlock_chrono.start();
            holder.unlock_button.setEnabled(false);
            holder.shValue.setText(String.format("%d", item.getShvalue()));
            holder.lshow.setVisibility(View.GONE);
            holder.lbuy.setVisibility(View.VISIBLE);

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements
            Chronometer.OnChronometerTickListener,
            View.OnClickListener {
        public FrameLayout lshow;
        public LinearLayout lbuy;
        public TextView description;
        public ImageView image;
        public ImageView buy_icon;
        public TextView shValue;
        public LinearLayout buy_button;
        public LinearLayout unlock_button;
        public TextView unlock_button_time;
        public Chronometer unlock_chrono;
        public long hintID;

        ViewHolder(View view) {
            super(view);
            lshow = view.findViewById(R.id.hint_item_show);
            lbuy = view.findViewById(R.id.hint_item_buy);

            description = lshow.findViewById(R.id.hint_item_description);
            image = lshow.findViewById(R.id.hint_item_image);

            shValue = lbuy.findViewById(R.id.hint_item_shvalue);
            buy_button = lbuy.findViewById(R.id.hint_item_buy_button);
            buy_icon = lbuy.findViewById(R.id.hint_item_buy_icon);
            unlock_button = lbuy.findViewById(R.id.hint_item_unlock_button);
            unlock_button_time = lbuy.findViewById(R.id.hint_item_unlock_button_time);
            unlock_chrono = lbuy.findViewById(R.id.hint_item_unlock_button_time_chrono);

            buy_button.setOnClickListener(this);
            unlock_button.setOnClickListener(this);
            unlock_chrono.setOnChronometerTickListener(this);
        }

        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (chronometer.getBase() < new Date().getTime()) {
                unlock_button_time.setVisibility(View.GONE);
                unlock_chrono.stop();

                unlock_button.setEnabled(true);
                unlock_button.setBackground(context.getResources().getDrawable(R.drawable.layout_button_selector));
                buy_button.setVisibility(View.GONE);
            } else {
                long diff = chronometer.getBase() - new Date().getTime();
                int seconds = ((int) (diff / 1000) % 60);
                int minutes = (int) (diff / 1000 / 60);
                String format = "%02d:%02d";
                unlock_button_time.setText(String.format(format, minutes, seconds));
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hint_item_buy_button:
                    if (buy_button.isEnabled()) {
                        mPresenter.buyHint(this.hintID);
                        mPresenter.fetchHints();
                    }
                    break;
                case R.id.hint_item_unlock_button:
                    if(unlock_button.isEnabled()) {
                        mPresenter.unlockHint(this.hintID);
                        mPresenter.fetchHints();
                    }
                    break;
            }
        }
    }
}
