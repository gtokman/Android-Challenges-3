package com.garytokman.tokmangary_ce01.fragments;

import android.app.Fragment;
import android.content.Intent;
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
import android.widget.Toast;

import com.garytokman.tokmangary_ce01.R;
import com.garytokman.tokmangary_ce01.activities.GenericActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Gary Tokman
// MDF3 - 1610
// FormFragment

public class FormFragment extends Fragment {

    private static final String TAG = FormFragment.class.getSimpleName();
    public static final String ACTION_SAVE = "com.fullsail.android.ACTION_SAVE_DATA";
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mAge;
    private List<EditText> mEditTexts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.form_layout, container, false);

        // Init
        mFirstName = (EditText) view.findViewById(R.id.first_name_text);
        mLastName = (EditText) view.findViewById(R.id.last_name_text);
        mAge = (EditText) view.findViewById(R.id.age_text);
        mEditTexts = new ArrayList<>(Arrays.asList(mFirstName, mLastName, mAge));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_action) {
            Log.d(TAG, "save button pressed ");
            if (isTextValid(mEditTexts)) {

                // package up the first, last, and age
                Intent intent = new Intent(ACTION_SAVE);
                intent.putExtra(GenericActivity.EXTRA_FIRST_NAME, getText(mFirstName));
                intent.putExtra(GenericActivity.EXTRA_LAST_NAME, getText(mLastName));
                intent.putExtra(GenericActivity.EXTRA_AGE, Integer.parseInt(getText(mAge)));
                getActivity().sendBroadcast(intent);


            } else {
                Toast.makeText(getActivity(), "No empty text", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }

    private boolean isTextValid(List<EditText> fields) {
        for (EditText text : fields) {
            if (text.getText().toString().isEmpty()) {
                text.setError("no empty fields");
                return false;
            }
        }
        return true;
    }
}
