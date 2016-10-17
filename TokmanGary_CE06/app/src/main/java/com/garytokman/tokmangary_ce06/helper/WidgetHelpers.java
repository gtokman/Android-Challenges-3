package com.garytokman.tokmangary_ce06.helper;
// Gary Tokman
// MDF3 - 1610
// WidgetHelpers

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.garytokman.tokmangary_ce06.R;
import com.garytokman.tokmangary_ce06.activity.ForecastActivity;
import com.garytokman.tokmangary_ce06.activity.MainActivity;
import com.garytokman.tokmangary_ce06.model.CurrentWeather;
import com.garytokman.tokmangary_ce06.model.Location;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WidgetHelpers {

    /*
    * http://api.wunderground.com/api/4a24d904d6e72011/forecast/q/CA/San_Francisco.json
    * http://api.wunderground.com/api/4a24d904d6e72011/conditions/q/FL/orlando.json
    * */

    public static final String BASE_URL = "http://api.wunderground.com/api/4a24d904d6e72011/";

    private static final String TAG = WidgetHelpers.class.getSimpleName();
    private static final String WEATHER_FILE = "WeatherFile";

    public static String getJsonWithUrl(String responseType, Location location) throws IOException {

        // Create Url
        URL url = new URL(BASE_URL + responseType + "/q/"
                + location.getState() + "/"
                + location.getCity() + ".json");

        // Connect
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();

        // Get stream
        InputStream inputStream = httpURLConnection.getInputStream();

        // Convert to string
        String json = IOUtils.toString(inputStream);

        // Close
        inputStream.close();
        httpURLConnection.disconnect();

        return json;
    }

    public static Location getPrefValue(Context context, String key) {

        // Get shared pref
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Location location;

        // Get value
        String value = sharedPreferences.getString(key, "Error");
        Log.d(TAG, "getPrefValue: " + value);

        /*Api is awful and does not allow to easily query by location like normal apis ex: location=?*/
        switch (value) {
            case "VALUE_ORLANDO":
                location = new Location("Orlando", "FL");
                break;
            case "VALUE_FRANCISCO":
                location = new Location("San_Francisco", "CA");
                break;
            case "VALUE_BOSTON":
                location = new Location("Boston", "MA");
                break;
            default:
                Log.d(TAG, "getPrefValue: Error========");
                location = new Location("Juneau", "AK");
                break;
        }

        return location;
    }

    public static void saveCurrentWeather(Context context, CurrentWeather currentWeather) throws IOException {
        // Create file
        File file = new File(context.getFilesDir(), WEATHER_FILE);

        // Create Output
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        // Wrap
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        // Save
        objectOutputStream.writeObject(currentWeather);

        Log.d(TAG, "saveCurrentWeather: saving...............");

        // Close
        fileOutputStream.close();
        objectOutputStream.close();

    }

    public static CurrentWeather loadCurrentWeather(Context context) throws IOException, ClassNotFoundException {
        // Get file
        File file = new File(context.getFilesDir(), WEATHER_FILE);

        // Create Input
        FileInputStream fileInputStream = new FileInputStream(file);

        // Wrap
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        // Read
        CurrentWeather currentWeather = (CurrentWeather) objectInputStream.readObject();

        Log.d(TAG, "loadCurrentWeather() returned: " + currentWeather.toString());

        fileInputStream.close();
        objectInputStream.close();

        return currentWeather;
    }

    public static void updateWidgetWithId(Context context, AppWidgetManager manager, int widgetId) {

        CurrentWeather currentWeather = null;

        try {
            currentWeather = loadCurrentWeather(context);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (currentWeather != null) {

            // Remote views
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            setWidgetIcon(context, widgetId, currentWeather, remoteViews);
            remoteViews.setTextViewText(R.id.conditionLabel, currentWeather.getDescription());
            remoteViews.setTextViewText(R.id.temperatureLabel, String.valueOf(currentWeather.getTemperature()) + " ºF");
            remoteViews.setTextViewText(R.id.timeLabel, currentWeather.getLastUpdate());

            // Click listener
            remoteViews.setOnClickPendingIntent(R.id.configButton, getPendingIntent(context, widgetId, MainActivity.class));
            remoteViews.setOnClickPendingIntent(R.id.weatherIcon, getPendingIntent(context, widgetId, ForecastActivity.class));

            // Update widget
            manager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private static void setWidgetIcon(final Context context, final int widgetId, CurrentWeather currentWeather, final RemoteViews remoteViews) {
        Handler handler = new Handler(Looper.getMainLooper());
        final CurrentWeather finalCurrentWeather = currentWeather;
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Init UI

                Picasso.with(context)
                        .load(finalCurrentWeather.getIconUrl()).resize(150, 150)
                        .into(remoteViews, R.id.weatherIcon, new int[widgetId]);
            }
        });
    }

    private static PendingIntent getPendingIntent(Context context, int widgetId, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
