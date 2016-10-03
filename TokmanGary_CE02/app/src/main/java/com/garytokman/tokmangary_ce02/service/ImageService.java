package com.garytokman.tokmangary_ce02.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.garytokman.tokmangary_ce02.activity.MainActivity;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

// Gary Tokman
// MDF3 - 1610
// ImageService

public class ImageService extends IntentService {

    private static final String TAG = ImageService.class.getSimpleName();
    public static final String[] IMAGES = {
            "MgmzpOJ.jpg", "VZmFngH.jpg", "ptE5z9u.jpg",
            "4QKO8Up.jpg", "Vm2UdDH.jpg", "C040ctB.jpg",
            "MScR8za.jpg", "tM1bsAH.jpg", "fS1lKZx.jpg",
            "h8e5rBX.jpg", "KBtUxzq.jpg", "wYXWJZz.jpg",
            "LOUwRC4.jpg", "7ZSQfIu.jpg", "XLJiKqp.jpg",
            "nXVLE9W.jpg", "HYQuj4b.jpg", "R8YIb8d.jpg",
            "cLv3TVc.jpg", "f7pMMdA.jpg", "Dl1aIHV.jpg",
            "UE3ng26.jpg", "1oyYfr0.jpg", "YSJ28fr.jpg",
            "Ey39hl5.jpg", "HAnhjCI.jpg", "En3J4ZF.jpg",
            "wr65Geg.jpg", "7D35kbV.jpg", "Z2WQBPI.jpg"
    };

    public ImageService() {
        super(ImageService.class.getSimpleName());
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Check if the image exists else download and save
        for (String image : IMAGES) {
            if (imageExists(image)) {
                sendLocalBroadCast();
            } else {
                if (isNetworkOn()) {
                    byte[] downloadImage = downloadImage(image);
                    writeImageToFile(downloadImage, image);
                    // update
                    sendLocalBroadCast();
                } else {
                    Toast.makeText(this, "Check network", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private boolean isNetworkOn() {
        // Get network info
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean imageExists(String imageName) {
        File file = new File(this.getFilesDir(), imageName);

        Log.d(TAG, "imageExists: " + file.exists());

        return file.exists();
    }

    private void writeImageToFile(byte[] image, String imageName) {

        try {
            // Open stream and write locally
            File file = new File(getFilesDir(), imageName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(image);
            fileOutputStream.close();
            Log.d(TAG, "writeImageToFile: " + imageName);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] downloadImage(String image) {

        HttpURLConnection httpURLConnection;

        try {
            // Download image
            String URL_BASE = "http://i.imgur.com/";
            URL url = new URL(URL_BASE + image);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            // Convert to bytes
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            Log.d(TAG, "downloadImage() returned: " + Arrays.toString(imageBytes));
            inputStream.close();
            httpURLConnection.disconnect();


            return imageBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    private void sendLocalBroadCast() {
        Intent intent = new Intent(MainActivity.ACTION_IMAGE_DOWNLOAD);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
