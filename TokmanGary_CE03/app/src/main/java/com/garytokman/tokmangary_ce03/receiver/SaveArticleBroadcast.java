package com.garytokman.tokmangary_ce03.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.garytokman.tokmangary_ce03.database.ArticleDatabase;
import com.garytokman.tokmangary_ce03.model.Article;
import com.garytokman.tokmangary_ce03.service.ArticleIntentService;

// Gary Tokman
// MDF3 - 1610
// SaveArticleBroadcast

public class SaveArticleBroadcast extends BroadcastReceiver {
    private static final String TAG = SaveArticleBroadcast.class.getSimpleName();
    public static final String UPDATE_LIST = "UPDATE_LIST";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: " + action);

        if (action.equals(ArticleIntentService.SAVE_BROADCAST)) {

            Article article = intent.getParcelableExtra(ArticleIntentService.NEWS_ARTICLE);

            if (article != null) {
                // Save
                ArticleDatabase.newInstance(context).saveArticle(article);
                sendUpdateBroadcast(context);
            }
        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            // Pending intent
            Intent intentService = new Intent(context, ArticleIntentService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intentService, 0);

            // Alarm manager
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60 * 1000, pendingIntent);
        }
    }

    private void sendUpdateBroadcast(Context context) {
        Intent intent = new Intent(UPDATE_LIST);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
