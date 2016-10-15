package com.garytokman.tokmangary_ce06.service;

import android.app.IntentService;
import android.content.Intent;

// Gary Tokman
// MDF3 - 1610
// WeatherService

public class WeatherService extends IntentService {
    public WeatherService() {
        super(WeatherService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
