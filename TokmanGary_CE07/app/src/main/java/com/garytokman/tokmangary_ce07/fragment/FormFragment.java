package com.garytokman.tokmangary_ce07.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.garytokman.tokmangary_ce07.R;

// Gary Tokman
// MDF3 - 1610
// FormFragment

public class FormFragment extends Fragment {

    private static final String TAG = FormFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText makeEditText = (EditText) view.findViewById(R.id.makeEditText);
        EditText modelEditText = (EditText) view.findViewById(R.id.modelEditText);
        EditText yearEditText = (EditText) view.findViewById(R.id.yearEditText);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveAction) {
            // TODO:SAVE
            return true;
        } else return super.onOptionsItemSelected(item);
    }
}
