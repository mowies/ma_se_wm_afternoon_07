package com.geoschnitzel.treasurehunt.shlist;

import android.os.Bundle;
import javax.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.geoschnitzel.treasurehunt.R;

public class SHListFragment extends Fragment implements  SHListContract.View{
    private SHListContract.Presenter mPresenter;

    public static SHListFragment newInstance() {
        return new SHListFragment();
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
    public void setPresenter(SHListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
