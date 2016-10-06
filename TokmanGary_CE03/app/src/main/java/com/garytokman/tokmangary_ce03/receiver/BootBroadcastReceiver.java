package com.garytokman.tokmangary_ce03.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.garytokman.tokmangary_ce03.service.ArticleIntentService;

// Gary Tokman
// MDF3 - 1610
// BootBroadcastReceiver

public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = BootBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
        Log.d(TAG, "onReceive: " + "booting up");
        // Pending intent
        Intent intentService = new Intent(context, ArticleIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intentService, 0);

        // Alarm manager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60 * 1000, pendingIntent);


    }
}
