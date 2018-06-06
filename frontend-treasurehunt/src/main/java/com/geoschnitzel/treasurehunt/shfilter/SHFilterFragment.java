package com.geoschnitzel.treasurehunt.shfilter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
public class SHFilterFragment extends DialogFragment implements SeekBar.OnSeekBarChangeListener {
    private static final String SEARCH_NAME = "search_name";

    private String mParam1;

    private OnFragmentInteractionListener mListener;
    private SeekBar mDistanceToSeekBar;
    private SeekBar mLengthHuntMinSeekBar;
    private SeekBar mLengthHuntMaxSeekBar;
    private SeekBar mRatingMinSeekBar;
    private SeekBar mRatingMaxSeekBar;
    private TextView mDistanceToTextView;
    private TextView mLengthHuntMinTextView;
    private TextView mLengthHuntMaxTextView;
    private TextView mRatingMinTextView;
    private TextView mRatingMaxTextView;
    private EditText mNameEdit;
    private EditText mAuthorEdit;

    public SHFilterFragment() {
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
        View root = inflater.inflate(R.layout.fragment_shfilter, null);
        builder.setView(root);

        this.mDistanceToSeekBar = (SeekBar) root.findViewById(R.id.shfilter_distance_to);

        this.mLengthHuntMinSeekBar = (SeekBar) root.findViewById(R.id.shfilter_length_hunt_min);
        this.mLengthHuntMaxSeekBar = (SeekBar) root.findViewById(R.id.shfilter_length_hunt_max);
        this.mRatingMinSeekBar = (SeekBar) root.findViewById(R.id.shfilter_min_rating);
        this.mRatingMaxSeekBar = (SeekBar) root.findViewById(R.id.shfilter_max_rating);
        this.mDistanceToTextView = (TextView) root.findViewById(R.id.shfilter_distance_to_text);
        this.mLengthHuntMaxTextView = (TextView) root.findViewById(R.id.shfilter_length_hunt_max_text);
        this.mLengthHuntMinTextView = (TextView) root.findViewById(R.id.shfilter_length_hunt_min_text);
        this.mRatingMinTextView = (TextView) root.findViewById(R.id.shfilter_rating_min_text);
        this.mRatingMaxTextView = (TextView) root.findViewById(R.id.shfilter_rating_max_text);
        this.mNameEdit = (EditText) root.findViewById(R.id.shfilter_name);
        this.mAuthorEdit = (EditText) root.findViewById(R.id.shfilter_author);

        this.mRatingMinSeekBar.setMax(4);
        this.mRatingMinSeekBar.setProgress(0);
        this.mRatingMaxSeekBar.setMax(4);
        this.mRatingMaxSeekBar.setProgress(4);

        this.mDistanceToSeekBar.setOnSeekBarChangeListener(this);
        this.mLengthHuntMinSeekBar.setOnSeekBarChangeListener(this);
        this.mLengthHuntMaxSeekBar.setOnSeekBarChangeListener(this);
        this.mRatingMinSeekBar.setOnSeekBarChangeListener(this);
        this.mRatingMaxSeekBar.setOnSeekBarChangeListener(this);

        builder.setTitle("Filter");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setNeutralButton("Reset", null);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SHFilterFragment.this.getDialog().cancel();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog_interface) {
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cleanFilter();
                    }
                });
            }
        });
        return dialog;
    }

    private void cleanFilter() {
        this.mNameEdit.setText("");
        this.mAuthorEdit.setText("");
        this.mDistanceToSeekBar.setProgress(this.mDistanceToSeekBar.getMax());
        this.mLengthHuntMinSeekBar.setProgress(0);
        this.mLengthHuntMaxSeekBar.setProgress(this.mLengthHuntMaxSeekBar.getMax());
        this.mRatingMinSeekBar.setProgress(0);
        this.mRatingMaxSeekBar.setProgress(4);
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

        return inflater.inflate(R.layout.fragment_shfilter, container, false);
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.shfilter_distance_to:
                this.mDistanceToTextView.setText(String.valueOf(progress) + " km");
                break;
            case R.id.shfilter_length_hunt_min:
                if (this.mLengthHuntMaxSeekBar.getProgress() < progress) {
                    this.mLengthHuntMaxSeekBar.setProgress(progress);
                }
                this.mLengthHuntMinTextView.setText(String.valueOf(progress) + " km");
                break;
            case R.id.shfilter_length_hunt_max:
                if (this.mLengthHuntMinSeekBar.getProgress() > progress) {
                    this.mLengthHuntMinSeekBar.setProgress(progress);
                }
                this.mLengthHuntMaxTextView.setText(String.valueOf(progress) + " km");
                break;
            case R.id.shfilter_max_rating:
                if (this.mRatingMinSeekBar.getProgress() > progress) {
                    this.mRatingMinSeekBar.setProgress(progress);
                }
                this.mRatingMaxTextView.setText(String.valueOf(progress + 1));
                break;
            case R.id.shfilter_min_rating:
                if (this.mRatingMaxSeekBar.getProgress() < progress) {
                    this.mRatingMaxSeekBar.setProgress(progress);
                }
                this.mRatingMinTextView.setText(String.valueOf(progress + 1));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
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
