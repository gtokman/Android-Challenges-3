package com.garytokman.tokmangary_ce06.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.garytokman.tokmangary_ce06.R;
import com.garytokman.tokmangary_ce06.WeatherPrefFragment;

public class MainActivity extends AppCompatActivity {

    private static final String WEATHER_PREF_FRAGMENT = "WEATHER_PREF_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init with fragment
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, new WeatherPrefFragment(), WEATHER_PREF_FRAGMENT)
                .commit();

    }
}
