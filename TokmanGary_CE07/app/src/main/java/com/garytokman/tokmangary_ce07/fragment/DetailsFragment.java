package com.garytokman.tokmangary_ce07.fragment;

import android.app.Fragment;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.database.CarDatabase;
import com.garytokman.tokmangary_ce07.database.DatabaseSchema.CarTable.Columns;
import com.garytokman.tokmangary_ce07.model.Car;
import com.garytokman.tokmangary_ce07.provider.CollectionProvider;

// Gary Tokman
// MDF3 - 1610
// DetailsFragment

public class DetailsFragment extends Fragment {

    private Car mCar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        TextView makeLabel = (TextView) view.findViewById(R.id.makeTextView);
        TextView modelLabel = (TextView) view.findViewById(R.id.modelTextView);
        TextView yearLabel = (TextView) view.findViewById(R.id.yearTextView);

        Intent intent = getActivity().getIntent();
        mCar = (Car) intent.getExtras().getSerializable(CarListFragment.EXTRA_CAR);

        // Set
        if (mCar != null) {
            makeLabel.setText(mCar.getMake());
            modelLabel.setText(mCar.getModel());
            yearLabel.setText(mCar.getYearString());
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.deleteAction) {
            // Delete
            String where = Columns.CAR_MAKE + " =? and " + Columns.CAR_MODEL + " =? and " + Columns.YEAR + " =?";
            String[] whereArgs = {mCar.getMake(), mCar.getModel(), mCar.getYearString()};
            CarDatabase.getInstance(getActivity()).deleteCar(where, whereArgs);

            // Update list, notify sends callback to onDataSetChanged
            AppWidgetManager manager = AppWidgetManager.getInstance(getActivity());
            int[] widgetIds = manager.getAppWidgetIds(new ComponentName(getActivity(), CollectionProvider.class));
            manager.notifyAppWidgetViewDataChanged(widgetIds, R.id.listView);


            getActivity().finish();

            return true;
        } else return super.onOptionsItemSelected(item);
    }
}
