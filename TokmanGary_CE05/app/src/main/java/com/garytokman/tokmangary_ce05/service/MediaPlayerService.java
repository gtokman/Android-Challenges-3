package com.garytokman.tokmangary_ce05.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.garytokman.tokmangary_ce05.R;

import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.COMPLETE;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.IDLE;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.PAUSE;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.PLAY;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.PREPARED;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.STOP;

// Gary Tokman
// MDF3 - 1610
// MediaPlayerService

/*
*  For example, when you create a new MediaPlayer, it is in the Idle state. At that point,
 *  you should initialize it by calling setDataSource(), bringing it to the Initialized state.
  *  After that, you have to prepare it using either the prepare() or prepareAsync() method.
   *  When the MediaPlayer is done preparing, it will then enter the Prepared state, which means
    *  you can call start() to make it play the media. At that point, as the diagram illustrates,
     *  you can move between the Started, Paused and PlaybackCompleted states by calling such methods
      *  as start(), pause(), and seekTo(), amongst others. When you call stop(), however, notice
       *  that you cannot call start() again until you prepare the MediaPlayer again.
       *  */

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener {


    enum STATE {
        PLAY, PAUSE, STOP, PREPARED, IDLE, COMPLETE
    }

    public static final String TAG = MediaPlayerService.class.getSimpleName();
    private MediaPlayer mPlayer;
    private STATE mSTATE = IDLE;


    public class MediaPlayerBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        prepareMediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return new MediaPlayerBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        // Destroy instance of media player
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            mSTATE = IDLE;
        }
    }

    public void play() {
        if (mSTATE == PREPARED || mSTATE == PAUSE || mSTATE == COMPLETE) {
            mPlayer.start();
            mSTATE = PLAY;
        }
    }

    public void pause() {
        if (mSTATE == PLAY) {
            mPlayer.pause();
            mSTATE = PAUSE;
        }
    }

    public void stop() {
        if (mSTATE == PLAY || mSTATE == PAUSE || mSTATE == COMPLETE) {
            mPlayer.stop();
            mSTATE = STOP;
            prepareMediaPlayer();
        }
    }

    private void prepareMediaPlayer() {
        if (mSTATE == IDLE || mSTATE == STOP) {
            mPlayer = MediaPlayer.create(this, R.raw.disclosure);
            mPlayer.setOnCompletionListener(this);
            mSTATE = PREPARED;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mSTATE = COMPLETE;
    }
}
