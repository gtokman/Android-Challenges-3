package com.garytokman.tokmangary_ce01.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

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
}
