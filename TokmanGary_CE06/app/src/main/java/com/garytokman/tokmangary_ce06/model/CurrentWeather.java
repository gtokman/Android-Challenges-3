package com.garytokman.tokmangary_ce06.model;
// Gary Tokman
// MDF3 - 1610
// CurrentWeather

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CurrentWeather implements Serializable{

    private String mDescription;
    private long mTemperature;
    private String mLastUpdate;
    private String mIconUrl;

    public CurrentWeather(JSONObject jsonObject) throws JSONException {

        mDescription = jsonObject.getString("weather");
        mTemperature = jsonObject.getLong("temp_f");
        mLastUpdate = jsonObject.getString("observation_time");
        mIconUrl = jsonObject.getString("icon_url");

    }

    public String getDescription() {
        return mDescription;
    }

    public long getTemperature() {
        return mTemperature;
    }

    public String getLastUpdate() {
        return mLastUpdate;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    @Override
    public String toString() {
        return mLastUpdate;
    }
}
