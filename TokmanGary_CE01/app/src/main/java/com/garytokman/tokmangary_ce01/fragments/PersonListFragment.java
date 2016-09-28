package com.garytokman.tokmangary_ce01.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.garytokman.tokmangary_ce01.activities.GenericActivity;
import com.garytokman.tokmangary_ce01.datebase.DatabaseSchema.PersonTable.Columns;
import com.garytokman.tokmangary_ce01.datebase.PersonDatabase;
import com.garytokman.tokmangary_ce01.model.Person;


// Gary Tokman
// MDF3 - 1610
// PersonListFragment

public class PersonListFragment extends ListFragment {

    private static final String TAG = PersonListFragment.class.getSimpleName();
    private CursorAdapter mCursorAdapter;
    private Cursor mPeople;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get saved people
        mPeople = PersonDatabase.getInstance(getActivity()).getPeople();

        String[] from = {Columns.FIRST_NAME, Columns.LAST_NAME};
        int[] to = {android.R.id.text1, android.R.id.text2};
        mCursorAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, mPeople, from, to, 1);
        setListAdapter(mCursorAdapter);
    }

    private void updateList() {
        mPeople = PersonDatabase.getInstance(getActivity()).getPeople();
        mCursorAdapter.changeCursor(mPeople);
        mCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        updateList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Get person
        Cursor personCursor = (Cursor) l.getAdapter().getItem(position);
        PersonCursorWrapper wrapper = new PersonCursorWrapper(personCursor);
        Person person = wrapper.getPerson();
        // Start detail activity
        Intent intent = new Intent(GenericActivity.ACTION_VIEW_DATA);
        intent.putExtra(GenericActivity.EXTRA_FIRST_NAME, person.getFirstName());
        intent.putExtra(GenericActivity.EXTRA_LAST_NAME, person.getLastName());
        intent.putExtra(GenericActivity.EXTRA_AGE, person.getAge());
        startActivity(intent);
    }

    private class PersonCursorWrapper extends CursorWrapper {

        PersonCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        Person getPerson() {
            return new Person(
                    getString(getColumnIndex(Columns.FIRST_NAME)),
                    getString(getColumnIndex(Columns.LAST_NAME)),
                    getInt(getColumnIndex(Columns.AGE)),
                    getActivity()
            );
        }
    }
}
