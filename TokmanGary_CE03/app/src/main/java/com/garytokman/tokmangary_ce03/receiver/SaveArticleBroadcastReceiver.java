package com.garytokman.tokmangary_ce03.receiver;

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
// SaveArticleBroadcastReceiver

public class SaveArticleBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = SaveArticleBroadcastReceiver.class.getSimpleName();
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
        }
    }

    private void sendUpdateBroadcast(Context context) {
        Intent intent = new Intent(UPDATE_LIST);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
