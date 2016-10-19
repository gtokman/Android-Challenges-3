package com.garytokman.tokmangary_ce07.helper;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.database.CarDatabase;
import com.garytokman.tokmangary_ce07.fragment.CarListFragment;
import com.garytokman.tokmangary_ce07.model.Car;

// Gary Tokman
// MDF3 - 1610
// CollectionWidgetFactory

public class CollectionWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = CollectionWidgetFactory.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;
    private int mWidgetId;

    public CollectionWidgetFactory(Context context, Intent intent) {
        mContext = context;
        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged: ");

        mCursor = CarDatabase.getInstance(mContext).getCars();
//        // Add new data and reload the list // Appwidget manager class
//        AppWidgetManager manager = AppWidgetManager.getInstance(mContext);
//        manager.notifyAppWidgetViewDataChanged(mWidgetId, R.id.listView);
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.d(TAG, "getViewAt: ");
        // Get cursor to set data source

        CursorHelper cursorHelper = new CursorHelper(mCursor);
        Car car = cursorHelper.getCar(i);


        // Create remote views
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_item);

        remoteViews.setTextViewText(R.id.carMakeItem, car.getMake());
        remoteViews.setTextViewText(R.id.modelItem, car.getModel());
        remoteViews.setTextViewText(R.id.carYearItem, car.getYearString());

        // Fill in intent
        Intent intent = new Intent();
        intent.putExtra(CarListFragment.EXTRA_CAR, car);
        remoteViews.setOnClickFillInIntent(R.id.listItem, intent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
