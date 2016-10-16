package com.garytokman.tokmangary_ce06.provider;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.garytokman.tokmangary_ce06.helper.WidgetHelpers;

// Gary Tokman
// MDF3 - 1610
// WeatherWidgetProvider

public class WeatherWidgetProvider extends AppWidgetProvider {
    private static final String TAG = WeatherWidgetProvider.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.d(TAG, "onUpdate: ======================= ");

        // Make call to network

        for (int appWidgetId : appWidgetIds) {
            WidgetHelpers.updateWidgetWithId(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
