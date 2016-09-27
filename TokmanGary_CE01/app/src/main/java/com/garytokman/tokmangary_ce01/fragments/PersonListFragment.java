package com.garytokman.tokmangary_ce01.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

// Gary Tokman
// MDF3 - 1610
// PersonListFragment

public class PersonListFragment extends ListFragment {

    private static final String ACTION_VIEW_DATA = "com.fullsail.android.ACTION_VIEW_DATA";
    public static final String EXTRA_FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    public static final String EXTRA_AGE = "com.fullsail.android.EXTRA_AGE";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("Gary");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Start detail activity
        Intent intent = new Intent(ACTION_VIEW_DATA);
        intent.putExtra(EXTRA_FIRST_NAME, "Gary");
        intent.putExtra(EXTRA_LAST_NAME, "Tokman");
        intent.putExtra(EXTRA_AGE, 22);
        startActivity(intent);
    }
}
