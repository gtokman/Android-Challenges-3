package com.garytokman.tokmangary_ce06.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.garytokman.tokmangary_ce06.helper.WidgetHelpers;
import com.garytokman.tokmangary_ce06.model.CurrentWeather;
import com.garytokman.tokmangary_ce06.model.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

// Gary Tokman
// MDF3 - 1610
// WeatherService

public class WeatherService extends IntentService {

    public static final String THEME_KEY = "THEME_PREFERENCE";
    public static final String LOCATION_KEY = "LOCATION_PREFERENCE";
    private static final String TAG = WeatherService.class.getSimpleName();


    public WeatherService() {
        super(WeatherService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Get location
        Location location = WidgetHelpers.getPrefValue(this, LOCATION_KEY);
        try {
            // Get json
            String json = WidgetHelpers.getJsonWithUrl("conditions", location);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject currentObservation = jsonObject.getJSONObject("current_observation");

            // Init
            CurrentWeather currentWeather = new CurrentWeather(currentObservation);

            Log.d(TAG, "onHandleIntent: " + currentWeather.getDescription() + " " + currentWeather.getIconUrl() + " " +
            currentWeather.getLastUpdate() + " " + currentWeather.getTemperature());

            // Save
            WidgetHelpers.saveCurrentWeather(this, currentWeather);


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}
