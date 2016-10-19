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

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.activity.DetailActivity;
import com.garytokman.tokmangary_ce07.activity.FormActivity;
import com.garytokman.tokmangary_ce07.database.CarDatabase;
import com.garytokman.tokmangary_ce07.database.DatabaseSchema.CarTable.Columns;
import com.garytokman.tokmangary_ce07.helper.CursorHelper;
import com.garytokman.tokmangary_ce07.model.Car;

// Gary Tokman
// MDF3 - 1610
// CarListFragment

public class CarListFragment extends ListFragment {

    public static final String EXTRA_CAR = "EXTRA_CAR";
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

        Cursor carCursor = (Cursor) l.getAdapter().getItem(position);
        CursorHelper helper = new CursorHelper(carCursor);
        Car car = helper.getCar(position);

        // Open detail
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(EXTRA_CAR, car);
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
