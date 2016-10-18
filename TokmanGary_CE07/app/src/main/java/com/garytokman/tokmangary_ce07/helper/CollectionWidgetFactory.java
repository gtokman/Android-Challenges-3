package com.garytokman.tokmangary_ce07.helper;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.activity.MainActivity;
import com.garytokman.tokmangary_ce07.model.Person;

import java.util.ArrayList;
import java.util.List;

// Gary Tokman
// MDF3 - 1610
// CollectionWidgetFactory

public class CollectionWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Person> mPersons;

    public CollectionWidgetFactory(Context context) {
        mContext = context;
        mPersons = new ArrayList<>();
    }

    @Override
    public void onCreate() {

        for (int i = 0; i < 10; i++) {
            mPersons.add(new Person("Gary"));
        }
    }

    @Override
    public void onDataSetChanged() {
        // Add new data and reload the list // Appwidget manager class
    }

    @Override
    public void onDestroy() {}

    @Override
    public int getCount() {
        return mPersons.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_item);

        Person person = mPersons.get(i);

        remoteViews.setTextViewText(R.id.personName, person.mName);

        // Fill in intent
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_PERSON, person);
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
