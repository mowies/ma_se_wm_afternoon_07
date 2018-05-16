package com.geoschnitzel.treasurehunt.shlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.shfilter.SHFilterFragment;
import com.geoschnitzel.treasurehunt.utils.BottomSheetListView;

import javax.annotation.Nullable;

public class SHListFragment extends BottomSheetDialogFragment implements SHListContract.View {
    private SHListContract.Presenter mPresenter = null;
    private BottomSheetBehavior mBottomSheetBehavior = null;

    public static SHListFragment newInstance() {
        return new SHListFragment();
    }

    @Override
    public void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_shlist, container, false);
        final BottomSheetListView shlist = root.findViewById(R.id.sh_list);
        final TextView temp = root.findViewById(R.id.temp_text_view);

        shlist.setAdapter(new SHListAdapter(mPresenter.getSHListItems(), getActivity().getApplicationContext()));

        this.mBottomSheetBehavior = BottomSheetBehavior.from(root.findViewById(R.id.main_sh_list_fragment));

        if (this.mBottomSheetBehavior != null) {
            this.mBottomSheetBehavior.setHideable(false);
            this.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            this.mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull final View bottomSheet, final int newState) {
                    getActivity().invalidateOptionsMenu();
                }

                @Override
                public void onSlide(@NonNull final View bottomSheet, final float slideOffset) {
//                    final float scaleFactor = 1 - slideOffset;
//                    if (mFab != null){
//                        if (scaleFactor <= 1){
//                            mFab.setVisibility(View.VISIBLE);
//                            mFab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
//                        }
//                        if (slideOffset == 1.00f){
//                            mFab.setVisibility(View.INVISIBLE);
//                        }
//                    }
                }
            });
        }

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shlist, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                DialogFragment filter_dialog = new SHFilterFragment();
                filter_dialog.show(this.getChildFragmentManager(), "SHFilterFragment");
                return true;
        }
        return false;
    }

    @Override
    public void setPresenter(SHListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
