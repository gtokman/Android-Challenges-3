package com.garytokman.tokmangary_ce07.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.activity.DetailActivity;
import com.garytokman.tokmangary_ce07.activity.FormActivity;
import com.garytokman.tokmangary_ce07.service.CollectionWidgetService;

// Gary Tokman
// MDF3 - 1610
// CollectionProvider

public class CollectionProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // Create remote views
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Start service that returns the remote view factory
        Intent serviceIntent = new Intent(context, CollectionWidgetService.class);

        // Set
        remoteViews.setRemoteAdapter(R.id.listView, serviceIntent);
        remoteViews.setEmptyView(R.id.listView, R.id.emptyText);

        // If empty list so text TODO: Make show detail
        remoteViews.setPendingIntentTemplate(R.id.listView, getPendingIntent(context, DetailActivity.class));
        remoteViews.setOnClickPendingIntent(R.id.formButton, getPendingIntent(context, FormActivity.class));

        // Update widget
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

    }

    private PendingIntent getPendingIntent(Context context, Class<?> activity) {
        // Set listener
        Intent intent = new Intent(context, activity);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
