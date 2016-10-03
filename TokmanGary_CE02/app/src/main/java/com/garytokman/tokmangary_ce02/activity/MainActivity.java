package com.garytokman.tokmangary_ce02.activity;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.garytokman.tokmangary_ce02.R;
import com.garytokman.tokmangary_ce02.fragment.GridViewFragment;

// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_IMAGE_DOWNLOAD = "ACTION_IMAGE_DOWNLOAD";
    private static final String GRID_VIEW_FRAGMENT = "GRID_VIEW_FRAGMENT";
    private static final String TAG = MainActivity.class.getSimpleName();
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: " + intent.getAction());
            mGridViewFragment.updateGridView();
        }
    };
    private GridViewFragment mGridViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void addFragment() {
        // Add fragment
        FragmentManager fragmentManager = getFragmentManager();
        mGridViewFragment = new GridViewFragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, mGridViewFragment, GRID_VIEW_FRAGMENT)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.downloadAction) {
            Log.d(TAG, "onOptionsItemSelected: ");
            addFragment();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ACTION_IMAGE_DOWNLOAD);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
}
