package com.garytokman.tokmangary_ce02.thread;
// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE02

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BitmapThread extends Thread {

    public interface UpdateUI {
        void getImage(Bitmap bitmap);
    }

    private String imageName;
    private Context mContext;
    private UpdateUI mUpdateUI;

    public BitmapThread(String imageName, Context context) {
        this.imageName = imageName;
        mContext = context;
        mUpdateUI = (UpdateUI) context;
    }

    @Override
    public void run() {
        mUpdateUI.getImage(loadImageData(imageName, mContext));
    }

    private Bitmap loadImageData(String image, Context context) {
        try {
            FileInputStream fileInputStream = context.openFileInput(image);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            Bitmap imageBitMap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);

            return imageBitMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
