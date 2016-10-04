package com.garytokman.tokmangary_ce03.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE03

public class NewsListFragment extends ListFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        setEmptyText("Hello World!");

    }
}
