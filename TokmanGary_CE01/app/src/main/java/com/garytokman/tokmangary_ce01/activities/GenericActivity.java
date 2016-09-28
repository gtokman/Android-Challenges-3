package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// GenericActivity

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.garytokman.tokmangary_ce01.R;

public abstract class GenericActivity extends AppCompatActivity {

    private static final String FRAGMENT = "FRAGMENT";
    protected static final String ACTION_UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";

    protected abstract Fragment getFragment();
    protected abstract int getMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        // Init layout with fragment
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, getFragment(), FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu with menu
        getMenuInflater().inflate(getMenu(), menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String message = null;

        switch (item.getItemId()) {
            case R.id.add_action:
                message = "Add action";
                // Start form activity
                Intent intent = new Intent(this, FormActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_action:
                message = "Delete action";
                finish();
                break;
            case R.id.save_action:
                message = "Save action";
                finish();
                break;
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}
