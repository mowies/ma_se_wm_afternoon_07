package com.geoschnitzel.treasurehunt.game;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.GameItem;
import com.geoschnitzel.treasurehunt.rest.HintItem;

import java.util.Date;
import java.util.List;

public class GameHintAdapter extends RecyclerView.Adapter<GameHintAdapter.ViewHolder> {

    GameContract.Presenter mPresenter;
    List<HintItem> items;
    Context context;

    public GameHintAdapter(List<HintItem> items, Context context, GameContract.Presenter mPresenter) {
        this.items = items;
        this.context = context;
        this.mPresenter = mPresenter;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return 0;
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
                    holder.image.setBackground(context.getResources().getDrawable(android.R.drawable.alert_dark_frame));
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

            holder.buytime.setBase(game.getCurrentTarget().getStartTime().getTime() + item.getTimeToUnlockHint() * 1000);
            holder.buytime.setOnChronometerTickListener(holder);

            holder.buytime.start();
            holder.buytime.setVisibility(View.VISIBLE);

            holder.lbuy.setVisibility(View.VISIBLE);

            holder.bbuy.setOnClickListener(holder);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements
            Chronometer.OnChronometerTickListener,
            View.OnClickListener {
        public LinearLayout lshow;
        public LinearLayout lbuy;
        public TextView description;
        public TextView image;
        public TextView shValue;
        public Button bbuy;
        public Chronometer buytime;
        public long hintID;

        ViewHolder(View view) {
            super(view);
            this.hintID = hintID;
            lshow = view.findViewById(R.id.hint_item_show);
            lbuy = view.findViewById(R.id.hint_item_buy);

            description = lshow.findViewById(R.id.hint_item_description);
            image = lshow.findViewById(R.id.hint_item_image);

            shValue = lbuy.findViewById(R.id.hint_item_shvalue);
            bbuy = lbuy.findViewById(R.id.hint_item_buy_button);
            buytime = lbuy.findViewById(R.id.hint_item_buy_time);
        }

        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (chronometer.getBase() >= new Date().getTime()) {
                bbuy.setText(R.string.hint_unlock);
                shValue.setVisibility(View.INVISIBLE);
                buytime.setVisibility(View.GONE);
                buytime.stop();
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hint_item_buy_button:
                    mPresenter.buyHint(this.hintID);
                    mPresenter.fetchHints();
            }
        }
    }
}
