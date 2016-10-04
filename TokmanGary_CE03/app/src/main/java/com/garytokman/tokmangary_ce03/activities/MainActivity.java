package com.garytokman.tokmangary_ce03.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.garytokman.tokmangary_ce03.R;
import com.garytokman.tokmangary_ce03.fragments.NewsListFragment;

// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity {

    private static final String NEWS_FRAGMENT = "NEWS_FRAGMENT";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Start Alarm to run every 60 seconds, alarm should trigger a service PendingIntent to start the service

        // Add fragment
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, new NewsListFragment(), NEWS_FRAGMENT)
                .commit();
    }
}
