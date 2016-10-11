package com.garytokman.tokmangary_ce05;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.garytokman.tokmangary_ce05.fragments.MediaPlayerFragment;
import com.garytokman.tokmangary_ce05.service.MediaPlayerService;

public class MainActivity extends AppCompatActivity implements ServiceConnection, MediaPlayerFragment.OnMediaControlSelected {

    private static final String FRAGMENT = "MediaPlayerFragment";
    private MediaPlayerService mMediaPlayerService;
    private MediaPlayerFragment mMediaPlayerFragment;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMediaPlayerFragment = new MediaPlayerFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, mMediaPlayerFragment, FRAGMENT)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBound = false;

        // Bind to service
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mBound = false;
        mMediaPlayerService = null;
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mBound = true;

        // Init service
        MediaPlayerService.MediaPlayerBinder localBinder = (MediaPlayerService.MediaPlayerBinder) iBinder;
        mMediaPlayerService = localBinder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mBound = false;
    }

    @Override
    public void onPlaySelected() {
        mMediaPlayerService.play();
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

    }

    @Override
    public void onSkipForwardSelected() {

    }
}
