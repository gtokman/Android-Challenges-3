package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// FromActivity

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.garytokman.tokmangary_ce01.R;
import com.garytokman.tokmangary_ce01.fragments.FormFragment;

public class FormActivity extends GenericActivity {

    @Override
    public Fragment getFragment() {
        return new FormFragment();
    }

    @Override
    protected int getMenu() {
        return R.menu.form_menu;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register
        IntentFilter intentFilter = new IntentFilter(ACTION_UPDATE_LIST);
        LocalBroadcastManager.getInstance(this).registerReceiver(mFormReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister
        unregisterReceiver(mFormReceiver);
    }

    private BroadcastReceiver mFormReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // When saved navigate to list and list refresh
        }
    };


}
