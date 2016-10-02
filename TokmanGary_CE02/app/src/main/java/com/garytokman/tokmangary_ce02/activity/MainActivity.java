package com.garytokman.tokmangary_ce02.activity;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.garytokman.tokmangary_ce02.R;
import com.garytokman.tokmangary_ce02.fragment.GridViewFragment;

// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity {

    private static final String GRID_VIEW_FRAGMENT = "GRID_VIEW_FRAGMENT";
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void addFragment() {
        // Add fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, new GridViewFragment(), GRID_VIEW_FRAGMENT)
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
}
