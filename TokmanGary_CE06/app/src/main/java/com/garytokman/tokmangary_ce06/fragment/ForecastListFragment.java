package com.garytokman.tokmangary_ce06.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

import com.garytokman.tokmangary_ce06.ForecastAdapter;
import com.garytokman.tokmangary_ce06.model.CurrentForecast;

import java.io.Serializable;
import java.util.List;


// Gary Tokman
// MDF3 - 1610
// ForecastListFragment

public class ForecastListFragment extends ListFragment  {

    private static final String FORECAST = "FORECAST";

    public static ForecastListFragment newInstance(List<CurrentForecast> forecasts) {
        // Save
        Bundle bundle = new Bundle();
        bundle.putSerializable(FORECAST, (Serializable) forecasts);

        // Set
        ForecastListFragment forecastListFragment = new ForecastListFragment();
        forecastListFragment.setArguments(bundle);

        return forecastListFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get args
        Bundle bundle = getArguments();

        // Get list
        List<CurrentForecast> forecasts = (List<CurrentForecast>) bundle.getSerializable(FORECAST);

        if (forecasts != null) {
            // Set adapter
            ForecastAdapter adapter = new ForecastAdapter(forecasts, getActivity());
            setListAdapter(adapter);
        }

    }
}
