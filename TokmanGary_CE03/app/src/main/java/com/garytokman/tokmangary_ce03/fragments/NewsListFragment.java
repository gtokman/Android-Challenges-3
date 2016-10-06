package com.garytokman.tokmangary_ce03.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.garytokman.tokmangary_ce03.R;
import com.garytokman.tokmangary_ce03.database.ArticleDatabase;
import com.garytokman.tokmangary_ce03.database.DatabaseSchema.PersonTable.Columns;

// Gary Tokman
// MDF3 - 1610
// NewsListFragment

public class NewsListFragment extends ListFragment {

    private CursorAdapter mCursorAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        setListShown(true);
        setEmptyText(getString(R.string.empty_list_text));

        // Article
        Cursor article = ArticleDatabase.newInstance(getActivity()).getArticle();

        // Set adapter
        String[] from = {Columns.TITLE, Columns.AUTHOR, Columns.DESC};
        int[] to = {R.id.articleTitle, R.id.articleAuthor, R.id.articleDescription};
        mCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item, article, from, to, 1);
        setListAdapter(mCursorAdapter);
    }

    public void updateList() {
        Cursor article = ArticleDatabase.newInstance(getActivity()).getArticle();
        mCursorAdapter.changeCursor(article);
        mCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Get cursor
        Cursor cursor = (Cursor) l.getAdapter().getItem(position);

        // Open web page
        String link = cursor.getString(cursor.getColumnIndex(Columns.URL));
        Uri webPage = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        startActivity(Intent.createChooser(intent, getString(R.string.chooser_text)));
    }
}
