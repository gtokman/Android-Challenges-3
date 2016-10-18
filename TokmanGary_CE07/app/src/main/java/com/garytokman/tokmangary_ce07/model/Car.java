package com.garytokman.tokmangary_ce07.model;
// Gary Tokman
// MDF3 - 1610
// Car

public class Car {

    private String mMake;
    private String mModel;
    private int mYear;

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

}
