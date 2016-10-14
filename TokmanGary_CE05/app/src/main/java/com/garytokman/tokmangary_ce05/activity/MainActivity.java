package com.garytokman.tokmangary_ce05.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.garytokman.tokmangary_ce05.R;
import com.garytokman.tokmangary_ce05.fragments.MediaPlayerFragment;
import com.garytokman.tokmangary_ce05.receiver.PlaybackReceiver;
import com.garytokman.tokmangary_ce05.service.MediaPlayerService;

// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity implements ServiceConnection, MediaPlayerFragment.OnMediaControlSelected {

    private static final String FRAGMENT = "MediaPlayerFragment";
    private static final String TAG = MainActivity.class.getSimpleName();

    private MediaPlayerService mMediaPlayerService;
    private MediaPlayerFragment mMediaPlayerFragment;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            mMediaPlayerFragment = new MediaPlayerFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mMediaPlayerFragment, FRAGMENT)
                    .commit();
        } else {
            mMediaPlayerFragment = (MediaPlayerFragment) getFragmentManager().findFragmentByTag(FRAGMENT);
            if (mMediaPlayerFragment == null) Log.d(TAG, "onCreate: null");
            else Log.d(TAG, "onCreate: not null");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBound = false;

        // Bind to service
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, this, BIND_AUTO_CREATE);

        IntentFilter filter = new IntentFilter(MediaPlayerService.ACTION);
        filter.addAction(PlaybackReceiver.ACTION_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            mBound = false;
            mMediaPlayerService = null;
            unbindService(this);
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: " + intent.getAction());

            String action = intent.getAction();
            if (action.equals(MediaPlayerService.ACTION)) {

                // Update UI on song change
                int index = intent.getIntExtra(MediaPlayerService.EXTRA_INDEX, 0);
                mMediaPlayerFragment.setSongInfo(index);
            } else if (action.equals(PlaybackReceiver.ACTION_UPDATE)) {

                // Notification play next / previous
                if (intent.hasExtra(PlaybackReceiver.EXTRA_UPDATE)) {
                    int i = intent.getExtras().getInt(PlaybackReceiver.EXTRA_UPDATE);
                    if (i == 1) mMediaPlayerService.playNext();
                    else mMediaPlayerService.playPrevious();
                }
            }
        }
    };

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mBound = true;

        // Init service
        MediaPlayerService.MediaPlayerBinder localBinder = (MediaPlayerService.MediaPlayerBinder) iBinder;
        mMediaPlayerService = localBinder.getService();
        mMediaPlayerService.setActivity(this);

        // When starting activity with pending intent check for current song index
        // After service binds with started foreground service
        // Update UI with current song
        mMediaPlayerFragment.setSongInfo(mMediaPlayerService.getCurrentSongIndex());
        mMediaPlayerFragment.setShuffle(mMediaPlayerService.getShuffle());
        mMediaPlayerFragment.setLooping(mMediaPlayerService.getLooping());
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mBound = false;
    }

    @Override
    public void onPlaySelected() {
        if (mBound) {
            mMediaPlayerService.play();
            // Foreground
            Intent intent = new Intent(this, MediaPlayerService.class);
            startService(intent);
        }
    }

    @Override
    public void onPauseSelected() {
        if (mBound) mMediaPlayerService.pause();
    }

    @Override
    public void onStopSelected() {
        if (mBound) mMediaPlayerService.stop();
    }

    @Override
    public void onSkipBackSelected() {
        if (mBound) mMediaPlayerService.playPrevious();
    }

    @Override
    public void onSkipForwardSelected() {
        if (mBound) mMediaPlayerService.playNext();
    }

    @Override
    public void onSeekBarChanged(final int change) {
        if (mBound) mMediaPlayerService.seekTo(change);
    }

    @Override
    public void onLoopSelected(boolean isLooping) {
        if (mBound) mMediaPlayerService.setLooping(isLooping);
    }

    @Override
    public void onShuffleSelected(boolean isShuffled) {
        if (mBound) mMediaPlayerService.setShuffle(isShuffled);
    }

    public void updateSeekBar(int duration, int position) {
        mMediaPlayerFragment.setSeekBarProgress(duration, position);
    }
}
