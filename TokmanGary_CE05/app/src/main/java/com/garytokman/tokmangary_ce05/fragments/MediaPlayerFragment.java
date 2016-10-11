package com.garytokman.tokmangary_ce05.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.garytokman.tokmangary_ce05.R;

// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE05

public class MediaPlayerFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = MediaPlayerFragment.class.getSimpleName();

    public interface OnMediaControlSelected {
        void onPlaySelected();

        void onPauseSelected();

        void onStopSelected();
    }

    private OnMediaControlSelected mSelected;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mSelected = (OnMediaControlSelected) context;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.media_player_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        // Init
        Button play = (Button) view.findViewById(R.id.playButton);
        Button pause = (Button) view.findViewById(R.id.pauseButton);
        Button stop = (Button) view.findViewById(R.id.stopButton);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playButton:
                mSelected.onPlaySelected();
                break;
            case R.id.pauseButton:
                mSelected.onPauseSelected();
                break;
            case R.id.stopButton:
                mSelected.onStopSelected();
                break;
        }
    }
}
