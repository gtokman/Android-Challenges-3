package com.garytokman.tokmangary_ce05.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.garytokman.tokmangary_ce05.R;
import com.garytokman.tokmangary_ce05.activity.MainActivity;
import com.garytokman.tokmangary_ce05.model.Song;
import com.garytokman.tokmangary_ce05.model.Songs;
import com.garytokman.tokmangary_ce05.receiver.PlaybackReceiver;

import java.util.List;

import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.COMPLETE;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.IDLE;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.PAUSE;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.PLAY;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.PREPARED;
import static com.garytokman.tokmangary_ce05.service.MediaPlayerService.STATE.STOP;

// Gary Tokman
// MDF3 - 1610
// MediaPlayerService

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    enum STATE {
        PLAY, PAUSE, STOP, PREPARED, IDLE, COMPLETE
    }

    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final String ACTION = "com.tokmangary_ce0.ACTION.SendShuffle";
    public static final List<Song> sSongs = Songs.getSongs();
    private static final String TAG = MediaPlayerService.class.getSimpleName();

    private MediaPlayer mPlayer;
    private Handler mHandler;
    private MainActivity mActivity;
    private boolean mShuffle = false;
    private STATE mSTATE;
    private Song mSong;
    private int mSongIndex = 0;

    // Setters

    public class MediaPlayerBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

    public void setActivity(MainActivity activity) {
        mActivity = activity;
    }

    public void setShuffle(boolean shuffle) {
        mShuffle = shuffle;
    }

    // Service life cycle

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate ");

        // Init
        mHandler = new Handler();

        // Prepare player
        mSTATE = IDLE;
        mSong = sSongs.get(mSongIndex);
        prepareMediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: ");
        startNotification();

        return Service.START_STICKY;
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

    // Client methods

    public void play() {
        if (mSTATE == PREPARED || mSTATE == PAUSE || mSTATE == COMPLETE) {
            mPlayer.start();
            updateSeekBar();
            mSTATE = PLAY;
        }
    }

    private void updateSeekBar() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mPlayer != null) {
                    // continue processing
                    mActivity.updateSeekBar(mPlayer.getDuration(), mPlayer.getCurrentPosition());
                    mHandler.postDelayed(this, 15);
                }
            }
        });
    }

    public void pause() {
        if (mSTATE == PLAY) {
            mPlayer.pause();
            stopForeground(true);
            mSTATE = PAUSE;
        }
    }

    public void stop() {
        if (mSTATE == PLAY || mSTATE == PAUSE || mSTATE == COMPLETE) {
            mPlayer.stop();
            mSTATE = STOP;
            stopForeground(true);
            prepareMediaPlayer();
        }
    }

    private void prepareMediaPlayer() {
        if (mSTATE == IDLE || mSTATE == STOP || mSTATE == PAUSE) {
            mPlayer = MediaPlayer.create(this, mSong.getSongId());
            mPlayer.setOnCompletionListener(this);
            mSTATE = PREPARED;
        }
    }

    public void playNext() {
        if (mSongIndex < sSongs.size() - 1) {
            mSong = sSongs.get(++mSongIndex);
            sendUpdateBroadcast(mSongIndex);
            if (mPlayer.isPlaying()) {
                stop();
                play();
                startNotification();
            } else prepareMediaPlayer();
        } else Toast.makeText(this, "End of list", Toast.LENGTH_SHORT).show();
    }

    public void playPrevious() {
        if (mSongIndex > 0) {
            mSong = sSongs.get(--mSongIndex);
            sendUpdateBroadcast(mSongIndex);
            if (mPlayer.isPlaying()) {
                stop();
                play();
                startNotification();
            } else prepareMediaPlayer();
        } else Toast.makeText(this, "Beginning of list", Toast.LENGTH_SHORT).show();
    }

    public void setLooping(boolean loop) {
        mPlayer.setLooping(loop);
    }

    public void seekTo(int place) {
        if (mSTATE == PLAY || mSTATE == PAUSE) {
            mPlayer.seekTo(place);
        }
    }

    public boolean getLooping() {
        return mPlayer.isLooping();
    }

    public boolean getShuffle() {
        return mShuffle;
    }

    public int getCurrentSongIndex() {
        return mSongIndex;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        // Change is complete
        mSTATE = COMPLETE;

        // If shuffle or loop // else stop foreground
        Log.d(TAG, "onCompletion: " + " shuffle " + mShuffle);
        if (mShuffle) {
            int random = (int) Math.round(Math.random() * (sSongs.size() - 1));
            Log.d(TAG, "onCompletion: " + random);
            sendUpdateBroadcast(random);
            mSong = sSongs.get(random);
            stop();
            play();
        } else if (!getLooping()) {
            stopForeground(true);
        }
    }

    private void sendUpdateBroadcast(int index) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(EXTRA_INDEX, index);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    // Notification

    private void startNotification() {
        NotificationCompat.Builder notificationBuilder = getBuilder();

        Notification notification = notificationBuilder.build();

        startForeground(1, notification);
    }

    private PendingIntent getIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @NonNull
    private NotificationCompat.Builder getBuilder() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setAutoCancel(false)
                .setContentTitle(getString(R.string.app_name))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), mSong.getImageId()))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(mSong.toString())
                .setContentIntent(getIntent())
                .addAction(android.R.drawable.ic_media_previous, "Previous", getPreviousIntent())
                .addAction(android.R.drawable.ic_media_next, "Next", getNextIntent());

        BigTextStyle bigTextStyle = new BigTextStyle();
        bigTextStyle
                .setBigContentTitle(mSong.getArtist())
                .bigText("Now playing " + mSong.toString());
        notificationBuilder.setStyle(bigTextStyle);
        return notificationBuilder;
    }

    private PendingIntent getPreviousIntent() {
        Log.d(TAG, "getPreviousIntent: ");
        Intent intent = new Intent(PlaybackReceiver.ACTION_BACKWARD);
        return PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getNextIntent() {
        Log.d(TAG, "getNextIntent: ");
        Intent intent = new Intent(PlaybackReceiver.ACTION_FORWARD);
        return PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
