package com.garytokman.tokmangary_ce06.helper;
// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE06

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WidgetHelpers {
    private static final String TAG = WidgetHelpers.class.getSimpleName();

    public static String getJsonWithUrl(String urlString) throws IOException {

        // Create Url
        URL url = new URL(urlString);

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

        Log.d(TAG, "getJsonWithUrl() returned: " + json);

        return json;
    }

    public static String getPrefValue(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(key, "Error");
    }
}
