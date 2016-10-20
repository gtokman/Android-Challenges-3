package com.garytokman.tokmangary_ce07.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.garytokman.tokmangary_ce07.helper.CollectionWidgetFactory;

// Gary Tokman
// MDF3 - 1610
// CollectionWidgetService

public class CollectionWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CollectionWidgetFactory(getApplicationContext());
    }
}
