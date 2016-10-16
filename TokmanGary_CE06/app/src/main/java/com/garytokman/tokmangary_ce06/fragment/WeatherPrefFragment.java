package com.garytokman.tokmangary_ce06.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.garytokman.tokmangary_ce06.R;

// Gary Tokman
// MDF3 - 1610
// WeatherPrefFragment

public class WeatherPrefFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.weather_pref);
    }
}
