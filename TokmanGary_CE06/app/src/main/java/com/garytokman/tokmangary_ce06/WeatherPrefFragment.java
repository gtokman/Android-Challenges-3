package com.garytokman.tokmangary_ce06;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;

// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE06

public class WeatherPrefFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addPreferencesFromResource(R.xml.weather_pref);
    }
}
