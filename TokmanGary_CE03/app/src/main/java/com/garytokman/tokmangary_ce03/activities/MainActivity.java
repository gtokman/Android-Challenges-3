package com.garytokman.tokmangary_ce03.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.garytokman.tokmangary_ce03.R;
import com.garytokman.tokmangary_ce03.fragments.NewsListFragment;
import com.garytokman.tokmangary_ce03.receiver.SaveArticleBroadcast;
import com.garytokman.tokmangary_ce03.service.ArticleIntentService;

// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity {

    private static final String NEWS_FRAGMENT = "NEWS_FRAGMENT";
    private static final String TAG = MainActivity.class.getSimpleName();
    private NewsListFragment mNewsListFragment;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Update list
            mNewsListFragment.updateList();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pending intent
        Intent intent = new Intent(this, ArticleIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        // TODO: Start Alarm to run every 60 seconds, alarm should trigger a service PendingIntent to start the service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60 * 1000, pendingIntent);


        // Add fragment
        mNewsListFragment = new NewsListFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, mNewsListFragment, NEWS_FRAGMENT)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(SaveArticleBroadcast.UPDATE_LIST);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }
}
