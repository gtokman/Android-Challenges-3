package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// DetailActivity

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.garytokman.tokmangary_ce01.fragments.DetailFragment;

public class DetailActivity extends GenericActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    public Fragment getFragment() {
        return new DetailFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register
        IntentFilter intentFilter = new IntentFilter(ACTION_UPDATE_LIST);
        LocalBroadcastManager.getInstance(this).registerReceiver(mDetailReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDetailReceiver);
    }

    private final BroadcastReceiver mDetailReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Once delete navigate back to list and list update
            Log.i(TAG, "onReceive: " + intent.getAction());
            finish();
        }
    };
}
