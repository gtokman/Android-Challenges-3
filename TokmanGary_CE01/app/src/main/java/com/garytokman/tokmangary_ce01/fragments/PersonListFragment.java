package com.garytokman.tokmangary_ce01.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.garytokman.tokmangary_ce01.activities.DetailActivity;

import java.util.ArrayList;
import java.util.List;

// Gary Tokman
// MDF3 - 1610
// PersonListFragment

public class PersonListFragment extends ListFragment {

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
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        String name = l.getAdapter().getItem(position).toString();
        intent.putExtra(Intent.EXTRA_TEXT, name);
        intent.putExtra(Intent.EXTRA_PHONE_NUMBER, 22);
        startActivity(intent);
    }
}
