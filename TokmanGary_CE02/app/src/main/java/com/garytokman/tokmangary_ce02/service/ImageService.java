package com.garytokman.tokmangary_ce02.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.garytokman.tokmangary_ce02.fragment.GridViewFragment;

// Gary Tokman
// MDF3 - 1610
// ImageService

public class ImageService extends IntentService {

    private final String URL_BASE = "http://i.imgur.com/";
    private final String[] IMAGES = {
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

    }

    private void writeImageToFile() {


    }

    private void downloadImage() {

    }

    private void sendLocalBroadCast() {
        Intent intent = new Intent(GridViewFragment.ACTION_IMAGE_DOWNLOAD);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
