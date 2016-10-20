package com.garytokman.tokmangary_ce07.model;
// Gary Tokman
// MDF3 - 1610
// Car

import java.io.Serializable;

public class Car implements Serializable {

    private final String mMake;
    private final String mModel;
    private final int mYear;

    public Car(String make, String model, int year) {
        mMake = make;
        mModel = model;
        mYear = year;
    }

    public String getMake() {
        return mMake;
    }

    public String getModel() {
        return mModel;
    }

    public int getYear() {
        return mYear;
    }

    public String getYearString() {
        return String.valueOf(mYear);
    }

    @Override
    public String toString() {
        return mMake + " " + mModel + " " + mYear;
    }
}
