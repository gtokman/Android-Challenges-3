package com.garytokman.tokmangary_ce04.model;
// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE04

import java.io.Serializable;

public class Photo implements Serializable {

    private String mPhotoDesc;
    private String mPhotoName;
    private String mPhotoUri;
    private double mLat;
    private double mLong;

    public Photo(String photoDesc, String photoName, String photoUri, double lat, double aLong) {
        mPhotoDesc = photoDesc;
        mPhotoName = photoName;
        mPhotoUri = photoUri;
        mLat = lat;
        mLong = aLong;
    }

    public String getPhotoDesc() {
        return mPhotoDesc;
    }

    public String getPhotoName() {
        return mPhotoName;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }


    public double getLat() {
        return mLat;
    }

    public double getLong() {
        return mLong;
    }

    public String getLatToString() {
        return String.valueOf(mLat);
    }

    public String getLongToString() {
        return String.valueOf(mLong);
    }

}
