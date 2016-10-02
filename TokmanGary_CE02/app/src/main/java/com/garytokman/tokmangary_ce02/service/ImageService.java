package com.garytokman.tokmangary_ce02.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.garytokman.tokmangary_ce02.fragment.GridViewFragment;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Gary Tokman
// MDF3 - 1610
// ImageService

public class ImageService extends IntentService {

    private static final String TAG = ImageService.class.getSimpleName();
    private final String URL_BASE = "http://i.imgur.com/";
    public final String[] IMAGES = {
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
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        for (String image : IMAGES) {
            if (imageExists(image)) {
                sendLocalBroadCast();
            } else {
                writeImageToFile(downloadImage(image), image);
            }
        }

                /*
Check if a local copy of the image already exists.
If the image already exists, send a broadcast to update the UI and move on to the next image.
If the image doesn't exist, download the image and save it according to the Storage section of the instructions, then send a broadcast to update the UI.
*/

    }

    private boolean imageExists(String imageName) {
        File file = new File(this.getFilesDir(), imageName);
        Log.d(TAG, "imageExists() returned: " + file.exists());
        return file.exists();
    }

    private void writeImageToFile(byte[] image, String imageName) {

        try {
            FileOutputStream fileOutputStream = openFileOutput(imageName, Context.MODE_PRIVATE);
            fileOutputStream.write(image);
            fileOutputStream.close();
            Log.d(TAG, "writeImageToFile: " + imageName);
            // update
            sendLocalBroadCast();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private byte[] downloadImage(String image) {

        // HTTP
        HttpURLConnection httpURLConnection;

        // URL
        try {
            // Create url
            URL url = new URL(URL_BASE + image);

            // Open connection
            httpURLConnection = (HttpURLConnection) url.openConnection();

            // Connect
            httpURLConnection.connect();

            // Read
            InputStream inputStream = httpURLConnection.getInputStream();

            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            Log.d(TAG, "downloadImage() returned: " + imageBytes);

            return imageBytes;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    private void sendLocalBroadCast() {
        Intent intent = new Intent(GridViewFragment.ACTION_IMAGE_DOWNLOAD);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
