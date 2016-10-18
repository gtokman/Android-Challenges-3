package com.garytokman.tokmangary_ce06.model;
// Gary Tokman
// MDF3 - 1610
// Location

public class Location {

    private final String mCity;
    private final String mState;

    public Location(String city, String state) {
        this.mCity = city;
        this.mState = state;
    }

    public String getCity() {
        return mCity;
    }

    public String getState() {
        return mState;
    }
}


