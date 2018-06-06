package com.geoschnitzel.treasurehunt.endgame;

import android.content.Intent;
import android.os.Bundle;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.base.BaseActivityWithBackButton;
import com.geoschnitzel.treasurehunt.main.MainActivity;
import com.geoschnitzel.treasurehunt.utils.ActivityUtils;

public class EndGameActivity extends BaseActivityWithBackButton {
    private EndGameContract.Presenter mPresenter = null;
    private EndGameFragment mEndGameFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mEndGameFragment =
                (EndGameFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (this.mEndGameFragment == null) {
            // Create the fragment
            this.mEndGameFragment = EndGameFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), this.mEndGameFragment, R.id.contentFrame, getString(R.string.fragment_tag_end_game));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }
}
