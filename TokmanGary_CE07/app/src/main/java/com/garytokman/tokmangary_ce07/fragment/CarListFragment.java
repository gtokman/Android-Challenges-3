package com.garytokman.tokmangary_ce07.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.activity.DetailActivity;
import com.garytokman.tokmangary_ce07.activity.FormActivity;
import com.garytokman.tokmangary_ce07.model.Car;

import java.util.ArrayList;
import java.util.List;

// Gary Tokman
// MDF3 - 1610
// CarListFragment

public class CarListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            cars.add(new Car("Ford", "Mustang", 2016));
        }

        ArrayAdapter<Car> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, cars);
        setListAdapter(arrayAdapter);

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
