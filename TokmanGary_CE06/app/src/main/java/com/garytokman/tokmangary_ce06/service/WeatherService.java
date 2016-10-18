package com.garytokman.tokmangary_ce06.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
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

    public static final String LOCATION_KEY = "LOCATION_PREFERENCE";
    private static final String TAG = WeatherService.class.getSimpleName();


    public WeatherService() {
        super(WeatherService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Get location
        Location location = WidgetHelpers.getPrefValue(this);
        try {
            // Get json
            String json = WidgetHelpers.getJsonWithUrl(location);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject currentObservation = jsonObject.getJSONObject("current_observation");

            // Init
            CurrentWeather currentWeather = new CurrentWeather(currentObservation);

            Log.d(TAG, "onHandleIntent: " + currentWeather.getDescription() + " " + currentWeather.getIconUrl() + " " +
            currentWeather.getLastUpdate() + " " + currentWeather.getTemperature());

            // Save
            WidgetHelpers.saveCurrentWeather(this, currentWeather);

            // Update
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            int widgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);

            WidgetHelpers.updateWidgetWithId(this, manager, widgetId);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}
