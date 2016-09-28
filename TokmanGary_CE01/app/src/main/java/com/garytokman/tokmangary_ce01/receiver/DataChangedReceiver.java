package com.garytokman.tokmangary_ce01.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.garytokman.tokmangary_ce01.activities.GenericActivity;
import com.garytokman.tokmangary_ce01.datebase.DatabaseSchema.PersonTable.Columns;
import com.garytokman.tokmangary_ce01.fragments.DetailFragment;
import com.garytokman.tokmangary_ce01.fragments.FormFragment;
import com.garytokman.tokmangary_ce01.model.Person;


// Gary Tokman
// MDF3 - 1610
// DataChangedReceiver

public class DataChangedReceiver extends BroadcastReceiver {
    private static final String TAG = DataChangedReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive:  " + intent.getAction());
        switch (intent.getAction()) {
            case FormFragment.ACTION_SAVE:
                savePerson(context, intent);
                break;
            case DetailFragment.ACTION_DELETE:
                deletePerson(context, intent);
                break;
        }

    }

    private void deletePerson(Context context, Intent intent) {
        Person person = getPerson(context, intent);

        Log.d(TAG, "deletePerson: " + person.toString());
        // Delete
        String whereClause = Columns.FIRST_NAME + " =? and " + Columns.LAST_NAME + " =? and " + Columns.AGE + " =?";
        String[] args = {person.getFirstName(), person.getLastName(), person.getAgeToString()};
        person.getPersonDatabase().deletePerson(whereClause, args);

        // Update list
        updateListBroadcast(context);

    }

    @NonNull
    private Person getPerson(Context context, Intent intent) {
        return new Person(
                    intent.getStringExtra(GenericActivity.EXTRA_FIRST_NAME),
                    intent.getStringExtra(GenericActivity.EXTRA_LAST_NAME),
                    intent.getIntExtra(GenericActivity.EXTRA_AGE, 0),
                    context
            );
    }

    private void savePerson(Context context, Intent intent) {
        Person person = getPerson(context, intent);

        Log.d(TAG, "savePerson: "  + person.toString());
        // Save
        person.getPersonDatabase().savePerson(person);

        // Send update list broadcast
        updateListBroadcast(context);

    }

    private void updateListBroadcast(Context context) {
        Intent intent = new Intent(GenericActivity.ACTION_UPDATE_LIST);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
