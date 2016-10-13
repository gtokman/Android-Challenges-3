package com.garytokman.tokmangary_ce05.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
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

import com.garytokman.tokmangary_ce05.R;
import com.garytokman.tokmangary_ce05.activity.MainActivity;
import com.garytokman.tokmangary_ce05.model.Song;
import com.garytokman.tokmangary_ce05.model.Songs;

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

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener {

    enum STATE {
        PLAY, PAUSE, STOP, PREPARED, IDLE, COMPLETE
    }

    public static final String EXTRA_INDEX = "random";
    public static final String ACTION = "com.tokmangary_ce0.ACTION.SendShuffle";
    public static final List<Song> sSongs = Songs.getSongs();
    private static final String TAG = MediaPlayerService.class.getSimpleName();


    private MediaPlayer mPlayer;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private Handler mHandler;
    private MainActivity mActivity;
    private boolean mLoop = false;
    private boolean mShuffle = false;
    private STATE mSTATE;
    private Song mSong;

    // Setters

    public class MediaPlayerBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

    public void setActivity(MainActivity activity) {
        mActivity = activity;
    }

    public void setLoop(boolean loop) {
        mLoop = loop;
    }

    public void setShuffle(boolean shuffle) {
        mShuffle = shuffle;
    }

    // Service life cycle

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        // Init
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mHandler = new Handler();

        // Prepare player
        mSTATE = IDLE;
        mSong = sSongs.get(0);
        prepareMediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: ");
        startNotification();

        return Service.START_NOT_STICKY;
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
                // continue processing
                mActivity.updateSeekBar(getDuration(), getCurrentPosition());
                mHandler.postDelayed(this, 15);
            }
        });
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
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

    public void prepareMediaPlayer() {
        if (mSTATE == IDLE || mSTATE == STOP) {
            mPlayer = MediaPlayer.create(this, mSong.getSongId());
            mPlayer.setOnCompletionListener(this);
            prepareNewNotification();
            mSTATE = PREPARED;
        }
    }

    public void playNext(int index) {
        mSong = sSongs.get(index);
    }

    public void playPrevious(int index) {
        mSong = sSongs.get(index);
    }

    public void seekTo(int place) {
        if (mSTATE == PLAY || mSTATE == PAUSE) {
            mPlayer.seekTo(place);
        }
    }

    public int getDuration() {
        return mPlayer.getDuration(); // milliseconds
    }

    public int getCurrentPosition() {
        return mPlayer.getCurrentPosition(); // milliseconds
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        // Change is complete
        mSTATE = COMPLETE;
        stopForeground(true);

        // Check if to loop or shuffle
        Log.d(TAG, "onCompletion: " + mLoop + " shuffle " + mShuffle);
        if (mLoop) {
            play();
        } else if (mShuffle) {
            int random = (int) Math.round(Math.random() * sSongs.size() - 1);
            sendUpdateBroadcast(random);
            mSong = sSongs.get(random);
            mSTATE = IDLE;
            mNotificationManager.notify(1, mNotification);
            play();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }


    private void sendUpdateBroadcast(int index) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(EXTRA_INDEX, index);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    // Notification

    private void startNotification() {
        NotificationCompat.Builder notificationBuilder = getBuilder();

        mNotification = notificationBuilder.build();

        startForeground(1, mNotification);
    }

    private void prepareNewNotification() {
        NotificationCompat.Builder notificationBuilder = getBuilder();

        mNotification = notificationBuilder.build();

        mNotificationManager.notify(1, mNotification);
    }

    private PendingIntent getIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", getCurrentPosition());
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @NonNull
    private NotificationCompat.Builder getBuilder() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
//                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), mSong.getImageId()))
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
        Intent intent = new Intent("");
        return PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getNextIntent() {
        Intent intent = new Intent("");
        return PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
