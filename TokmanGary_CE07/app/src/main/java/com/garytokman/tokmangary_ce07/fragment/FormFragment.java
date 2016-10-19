package com.garytokman.tokmangary_ce07.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.database.CarDatabase;
import com.garytokman.tokmangary_ce07.model.Car;

// Gary Tokman
// MDF3 - 1610
// FormFragment

public class FormFragment extends Fragment {

    private static final String TAG = FormFragment.class.getSimpleName();
    private EditText mMakeEditText;
    private EditText mModelEditText;
    private EditText mYearEditText;

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

        mMakeEditText = (EditText) view.findViewById(R.id.makeEditText);
        mModelEditText = (EditText) view.findViewById(R.id.modelEditText);
        mYearEditText = (EditText) view.findViewById(R.id.yearEditText);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveAction) {

            // Check if text fields are valid
            if (isEmptyText(mMakeEditText) || isEmptyText(mModelEditText) || isEmptyText(mYearEditText)) {
                return false;
            }

            // Save car
            Car car = new Car(getText(mMakeEditText), getText(mModelEditText), Integer.valueOf(getText(mYearEditText)));
            Log.d(TAG, "onOptionsItemSelected: " + car.toString());
            CarDatabase.getInstance(getActivity()).saveCar(car);
            getActivity().finish();

            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }

    private boolean isEmptyText(EditText editText) {
        return getText(editText).isEmpty();
    }
}
