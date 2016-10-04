package com.garytokman.tokmangary_ce03.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

// Gary Tokman
// MDF3 - 1610
// NewsIntentService

public class NewsIntentService extends IntentService {


    private static final String NEWS_URL = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=6bac43a15cfd4f309f73ec5874562c76";
    private static final String TAG = NewsIntentService.class.getSimpleName();

    public NewsIntentService() {
        super(NewsIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private String downLoadArticle() {

        HttpURLConnection httpURLConnection;

        try {
            // Connect
            URL url = new URL(NEWS_URL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            // Get json from stream
            FileInputStream fileInputStream = (FileInputStream) httpURLConnection.getInputStream();
            String json = IOUtils.toString(fileInputStream);

            // Disconnect
            fileInputStream.close();
            httpURLConnection.disconnect();

            Log.d(TAG, "downLoadArticle() returned: " + json);

            return json;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
