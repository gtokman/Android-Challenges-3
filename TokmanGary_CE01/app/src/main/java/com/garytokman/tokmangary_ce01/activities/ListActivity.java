package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// ListActivity

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.garytokman.tokmangary_ce01.R;
import com.garytokman.tokmangary_ce01.fragments.PersonListFragment;

public class ListActivity extends GenericActivity {
    private static final String TAG = ListActivity.class.getSimpleName();

    @Override
    public Fragment getFragment() {
        return new PersonListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_action) {
            Log.d(TAG, "Add action ");
            Intent intent = new Intent(this, FormActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);

        }
    }
}
