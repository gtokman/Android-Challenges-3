package com.garytokman.tokmangary_ce05.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.garytokman.tokmangary_ce05.R;
import com.garytokman.tokmangary_ce05.model.Song;

// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE05

public class MediaPlayerFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = MediaPlayerFragment.class.getSimpleName();
    private ImageView mAlbumArt;
    private TextView mSongInfo;
    private SeekBar mSeekBar;

    public interface OnMediaControlSelected {
        void onPlaySelected();

        void onPauseSelected();

        void onStopSelected();

        void onSkipBackSelected();

        void onSkipForwardSelected();
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
        return  inflater.inflate(R.layout.media_player_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        // Init
        view.findViewById(R.id.play).setOnClickListener(this);
        view.findViewById(R.id.pause).setOnClickListener(this);
        view.findViewById(R.id.stop).setOnClickListener(this);
        view.findViewById(R.id.skipForward).setOnClickListener(this);
        view.findViewById(R.id.skipBack).setOnClickListener(this);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        mSeekBar.setMax(100);
        mSongInfo = (TextView) view.findViewById(R.id.songTitle);
        mAlbumArt = (ImageView) view.findViewById(R.id.albumArt);
    }

    public void setSongInfo(Song song) {
        mAlbumArt.setImageResource(song.getImageId());
        mSongInfo.setText(song.toString());
    }

    public void setSeekBarProgress(int duration, int progress) {
        mSeekBar.setMax(duration * 1000);
        mSeekBar.setProgress(progress * 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                mSelected.onPlaySelected();
                break;
            case R.id.pause:
                mSelected.onPauseSelected();
                break;
            case R.id.stop:
                mSelected.onStopSelected();
                break;
            case R.id.skipBack:
                mSelected.onSkipBackSelected();
                break;
            case R.id.skipForward:
                mSelected.onSkipForwardSelected();
                break;
        }
    }
}
