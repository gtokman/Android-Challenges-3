package com.garytokman.tokmangary_ce06.helper;
// Gary Tokman
// MDF3 - 1610
// WeatherTask


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.garytokman.tokmangary_ce06.model.Location;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherTask extends AsyncTask<String, Void, String> {

    private ProgressDialog mDialog;

    public interface OnWeatherTaskComplete {
        void getForecastJson(String json);
    }

    private final Location mLocation;
    private final Context mContext;
    private OnWeatherTaskComplete mComplete;


    public WeatherTask(Location location, Context context) {
        mLocation = location;
        mContext = context;

        try {
            mComplete = (OnWeatherTaskComplete) context;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("Loading forecast for " + mLocation.getCity());
        mDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            // Create Url
            URL url = new URL(WidgetHelpers.BASE_URL + strings[0] + "/q/"
                    + mLocation.getState() + "/"
                    + mLocation.getCity() + ".json");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();

            String data = IOUtils.toString(inputStream);

            inputStream.close();
            httpURLConnection.disconnect();

            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);

        if (data != null) {

            mDialog.dismiss();
            mComplete.getForecastJson(data);

        }

    }
}