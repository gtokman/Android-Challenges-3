package com.garytokman.tokmangary_ce07.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.activity.DetailActivity;
import com.garytokman.tokmangary_ce07.activity.FormActivity;
import com.garytokman.tokmangary_ce07.database.CarDatabase;
import com.garytokman.tokmangary_ce07.database.DatabaseSchema.CarTable.Columns;

// Gary Tokman
// MDF3 - 1610
// CarListFragment

public class CarListFragment extends ListFragment {

    private SimpleCursorAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Cursor cars = CarDatabase.getInstance(getActivity()).getCars();
        setEmptyText("No Data...");

        String[] from = {Columns.CAR_MAKE, Columns.CAR_MODEL, Columns.YEAR};
        int[] to = {R.id.carMakeItem, R.id.modelItem, R.id.carYearItem};

        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item, cars, from, to, 1);
        setListAdapter(mAdapter);

    }

    private void updateList() {
        Cursor cars = CarDatabase.getInstance(getActivity()).getCars();
        mAdapter.changeCursor(cars);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), "Cars", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.addAction) {
            // Show form
            Intent intent = new Intent(getActivity(), FormActivity.class);
            startActivity(intent);

            return true;
        } else return super.onOptionsItemSelected(item);
    }
}
