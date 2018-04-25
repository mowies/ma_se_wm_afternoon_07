package com.geoschnitzel.treasurehunt.shfilter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.geoschnitzel.treasurehunt.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SHFilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SHFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SHFilterFragment extends DialogFragment {
    private static final String SEARCH_NAME = "search_name";

    private String mParam1;

    private OnFragmentInteractionListener mListener;
    private SeekBar mDistanceSeekBar;
    private SeekBar mRatingSeekBar;
    private TextView mDistanceTextView;
    private TextView mRatingTextView;
    private SeekBar.OnSeekBarChangeListener mDistanceChangeListener;
    private SeekBar.OnSeekBarChangeListener mRatingChangeListener;

    public SHFilterFragment() {
        this.mDistanceChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDistanceTextView.setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mDistanceTextView.setText("999");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mDistanceTextView.setText("999");
            }
        };

        this.mRatingChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRatingTextView.setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mDistanceTextView.setText("999");

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mDistanceTextView.setText("999");

            }
        };
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param search_name Parameter 1.
     * @return A new instance of fragment SHFilterFragment.
     */
    public static SHFilterFragment newInstance(String search_name) {
        SHFilterFragment fragment = new SHFilterFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_NAME, search_name);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_shfilter, null));
        builder.setTitle("Filter");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SHFilterFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(SEARCH_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_shfilter, container, false);

        this.mDistanceSeekBar = (SeekBar) layout.findViewById(R.id.shfilter_distance);
        this.mRatingSeekBar = (SeekBar) layout.findViewById(R.id.shfilter_rating);
        this.mDistanceTextView = (TextView) layout.findViewById(R.id.shfilter_distance_text);
        this.mRatingTextView = (TextView) layout.findViewById(R.id.shfilter_rating_text);

        this.mDistanceSeekBar.setOnSeekBarChangeListener(this.mDistanceChangeListener);
        this.mRatingSeekBar.setOnSeekBarChangeListener(this.mRatingChangeListener);
        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
