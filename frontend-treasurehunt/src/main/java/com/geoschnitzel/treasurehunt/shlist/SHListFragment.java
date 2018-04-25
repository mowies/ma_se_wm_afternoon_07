package com.geoschnitzel.treasurehunt.shlist;

import android.os.Bundle;
import javax.annotation.Nullable;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.shfilter.SHFilterFragment;

public class SHListFragment extends Fragment implements  SHListContract.View{
    private SHListContract.Presenter mPresenter;

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
        View root = inflater.inflate(R.layout.fragment_shlist, container, false);
        ListView shlist = root.findViewById(R.id.sh_list);
        shlist.setAdapter(new SHListAdapter(mPresenter.getSHListItems(), getActivity().getApplicationContext()));

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shlist, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filter:
                DialogFragment filter_dialog = new SHFilterFragment();
                filter_dialog.show(getActivity().getSupportFragmentManager(), "SHFilterFragment");
                return true;
        }
        return false;
    }

    @Override
    public void setPresenter(SHListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
