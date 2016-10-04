package com.garytokman.tokmangary_ce03.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.garytokman.tokmangary_ce03.R;

// Gary Tokman
// MDF3 - 1610
// NewsListFragment

public class NewsListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        setListShown(true);
        setEmptyText(getString(R.string.empty_list_text));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // TODO: Load in web view
        Uri webPage = Uri.parse("");
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        startActivity(Intent.createChooser(intent, getString(R.string.chooser_text)));


    }
}
