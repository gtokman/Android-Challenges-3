package com.garytokman.tokmangary_ce06.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.garytokman.tokmangary_ce06.R;
import com.garytokman.tokmangary_ce06.fragment.WeatherPrefFragment;
import com.garytokman.tokmangary_ce06.service.WeatherService;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;
import static com.garytokman.tokmangary_ce06.helper.WidgetHelpers.THEME_KEY;

// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity {

    private static final String WEATHER_PREF_FRAGMENT = "WEATHER_PREF_FRAGMENT";
    private static final String TAG = MainActivity.class.getSimpleName();
    private int mWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateTheme();

        // Init with fragment
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, new WeatherPrefFragment(), WEATHER_PREF_FRAGMENT)
                .commit();

        // Get widget id that start the activity
        Intent intent = getIntent();
        mWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);

        // Guard against bad widget id
        if (mWidgetId == INVALID_APPWIDGET_ID) finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.config_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {

            Log.d(TAG, "onOptionsItemSelected: ");

            // Start service
            Intent intent = new Intent(this, WeatherService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
            startService(intent);

            // Update app theme
            updateTheme();

            // After update tell the system the update is successful, use intent, put extra widget id, and set result
            Intent data = new Intent();
            data.putExtra(EXTRA_APPWIDGET_ID, mWidgetId);
            setResult(RESULT_OK, data);
            finish();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void updateTheme() {
        // Get value
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPreferences.getString(THEME_KEY, "VALUE_LIGHT");

        // Check
        if (theme.equals("VALUE_DARK")) {
            setTheme(R.style.Dark);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303334")));
            }
        } else {
            setTheme(R.style.AppTheme);
        }
    }
}
