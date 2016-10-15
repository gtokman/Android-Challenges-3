package com.garytokman.tokmangary_ce06.model;
// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE06

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentForecast {

    private String mDay;
    private String mHighTemperature;
    private String mLowTemperature;
    private String mIconUrl;
    private String mConditions;

    public CurrentForecast(JSONObject jsonObject) throws JSONException {
        mDay = jsonObject.getJSONObject("date").getString("weekday");
        mHighTemperature = jsonObject.getJSONObject("high").getString("fahrenheit");
        mLowTemperature = jsonObject.getJSONObject("low").getString("fahrenheit");
        mIconUrl = jsonObject.getString("icon_url");
        mConditions = jsonObject.getString("conditions");
    }

    public String getDay() {
        return mDay;
    }

    public String getHighTemperature() {
        return mHighTemperature;
    }

    public String getLowTemperature() {
        return mLowTemperature;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public String getConditions() {
        return mConditions;
    }
}
