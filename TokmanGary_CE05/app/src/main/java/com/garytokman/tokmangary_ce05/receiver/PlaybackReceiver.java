package com.garytokman.tokmangary_ce05.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

// Gary Tokman
// MDF3 - 1610
// PlaybackReceiver

public class PlaybackReceiver extends BroadcastReceiver {

    public static final String ACTION_FORWARD = "com.garytokman_ce0.ACTION.PlayBackForward";
    public static final String ACTION_BACKWARD = "com.garytokman_ce0.ACTION.PlayBackBackward";
    public static final String ACTION_UPDATE = "com.garytokman_ce0.ACTION.Update";
    public static final String EXTRA_UPDATE = "EXTRA_UPDATE";
    private static final String TAG = PlaybackReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: " + intent.getAction());
        String action = intent.getAction();
        if (action.equals(ACTION_FORWARD)) {
            updateSong(context, 1);

        } else if (action.equals(ACTION_BACKWARD)) {
            updateSong(context, 2);
        }
    }

    private void updateSong(Context context, int i) {
        Intent intent = new Intent(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, i);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
