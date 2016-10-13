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
import com.garytokman.tokmangary_ce05.service.MediaPlayerService;

public class MainActivity extends AppCompatActivity implements ServiceConnection, MediaPlayerFragment.OnMediaControlSelected {

    private static final String FRAGMENT = "MediaPlayerFragment";
    private static final String TAG = MainActivity.class.getSimpleName();
    private MediaPlayerService mMediaPlayerService;
    public MediaPlayerFragment mMediaPlayerFragment;
    private boolean mBound;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int index = intent.getIntExtra(MediaPlayerService.EXTRA_INDEX, 0);
            mMediaPlayerFragment.setSongInfo(index);
        }
    };

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

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mBound = true;

        // Init service
        MediaPlayerService.MediaPlayerBinder localBinder = (MediaPlayerService.MediaPlayerBinder) iBinder;
        mMediaPlayerService = localBinder.getService();
        mMediaPlayerService.setActivity(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mBound = false;
    }

    @Override
    public void onPlaySelected() {
        mMediaPlayerService.play();

        // Foreground
        Intent intent = new Intent(this, MediaPlayerService.class);
        startService(intent);
    }

    @Override
    public void onPauseSelected() {
        mMediaPlayerService.pause();
    }

    @Override
    public void onStopSelected() {
        mMediaPlayerService.stop();
    }

    @Override
    public void onSkipBackSelected() {
        mMediaPlayerService.playPrevious();
    }


    @Override
    public void onSkipForwardSelected() {
        mMediaPlayerService.playNext();
    }

    @Override
    public void onSeekBarChanged(final int change) {
        mMediaPlayerService.seekTo(change);
    }

    @Override
    public void onLoopSelected(boolean isLooping) {
        Log.d(TAG, "onLoopSelected: " + isLooping);
        mMediaPlayerService.isLooping(isLooping);
    }

    @Override
    public void onShuffleSelected(boolean isShuffled) {
        mMediaPlayerService.setShuffle(isShuffled);
    }

    public void updateSeekBar(int duration, int position) {
        mMediaPlayerFragment.setSeekBarProgress(duration, position);
    }

}
