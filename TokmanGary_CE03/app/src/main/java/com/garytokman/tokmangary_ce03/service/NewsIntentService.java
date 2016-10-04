package com.garytokman.tokmangary_ce03.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.garytokman.tokmangary_ce03.R;
import com.garytokman.tokmangary_ce03.model.Article;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Gary Tokman
// MDF3 - 1610
// NewsIntentService

public class NewsIntentService extends IntentService {


    private static final String NEWS_URL = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=6bac43a15cfd4f309f73ec5874562c76";
    private static final String TAG = NewsIntentService.class.getSimpleName();
    private static final String SAVE_BROADCAST = "SAVE_BROADCAST";

    public NewsIntentService() {
        super(NewsIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String json = downLoadArticle();
        try {
            Notification notification = getNotification(getRandomArticle(json));
            manager.notify(1, notification);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String downLoadArticle() {

        HttpURLConnection httpURLConnection;

        try {
            // Connect
            URL url = new URL(NEWS_URL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            // Get json from stream
            InputStream fileInputStream = httpURLConnection.getInputStream();
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

    private Article getRandomArticle(String json) throws JSONException {

        // Covert to json object
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("articles");

        return new Article(jsonArray);
    }

    private Notification getNotification(Article article) {

        // Create
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder
                .setContentTitle(article.getTitle())
                .setContentText(article.getDescription())
                .setSmallIcon(R.mipmap.ic_launcher)
                .addAction(R.drawable.ic_cloud_save, "Save", getSaveIntent())
                .setAutoCancel(true)
                .setContentIntent(openWebPage(article.getUrl()));


        BigTextStyle bigTextStyle = new BigTextStyle();
        bigTextStyle
                .setBigContentTitle(article.getTitle())
                .setSummaryText(article.getAuthor())
                .bigText(article.getDescription());
        builder.setStyle(bigTextStyle);

        return builder.build();
    }

    private PendingIntent getSaveIntent() {
        Intent intent = new Intent(SAVE_BROADCAST);

        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private PendingIntent openWebPage(String link) {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
