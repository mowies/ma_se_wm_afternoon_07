package com.geoschnitzel.treasurehunt.shlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.rest.SHListItem;
import com.geoschnitzel.treasurehunt.shfilter.SHFilterFragment;
import com.geoschnitzel.treasurehunt.utils.BottomSheetListView;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class SHListFragment extends BottomSheetDialogFragment implements SHListContract.View, View.OnClickListener {
    private SHListContract.Presenter mPresenter = null;
    public BottomSheetBehavior mBottomSheetBehavior = null;
    private FloatingActionButton mAddFab = null;
    private BottomSheetListView mSHList = null;
    private LinearLayout mFilterInfo = null;
    private ImageView mSHListArrowUp = null;

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
        this.mSHList = root.findViewById(R.id.sh_list);
        this.mAddFab = getActivity().findViewById(R.id.floatingAddButton);
        this.mFilterInfo = root.findViewById(R.id.filter_info);
        this.mSHListArrowUp = root.findViewById(R.id.sh_list_arrow_up);
        this.mFilterInfo.setAlpha(1.0f);

        mPresenter.retrieveSHListItems();
        this.refreshSHListAdapter(new ArrayList<>());

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

                    final float scaleFactor = 1 - slideOffset;
                    if (mAddFab != null) {
                        if (scaleFactor <= 1) {
                            mAddFab.setVisibility(View.VISIBLE);
                            mAddFab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
                        }
                        if (slideOffset == 1.00f) {
                            mAddFab.setVisibility(View.INVISIBLE);
                        }
                    }

                    if(mFilterInfo != null) {
                        if (scaleFactor <= 1) {
                            mFilterInfo.setVisibility(View.VISIBLE);
                            mFilterInfo.animate().scaleY(1 - slideOffset).setDuration(0).start();
                        }
                        if (slideOffset >= 0.99f) {
                            mFilterInfo.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }

        this.mFilterInfo.setOnClickListener(this);

        return root;
    }

    public void closeBottomSheet() {
        this.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

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

    @Override
    public void onClick(View v) {
        this.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void refreshSHListAdapter(List<SHListItem> items) {

        SHListAdapter adpater = new SHListAdapter(items, getActivity().getApplicationContext());
        mSHList.setAdapter(adpater);
        mSHList.setOnItemClickListener(adpater);
    }
}
