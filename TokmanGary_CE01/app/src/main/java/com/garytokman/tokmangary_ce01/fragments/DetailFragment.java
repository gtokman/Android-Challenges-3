package com.garytokman.tokmangary_ce01.fragments;

import android.app.Fragment;
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

import com.garytokman.tokmangary_ce01.R;
import com.garytokman.tokmangary_ce01.activities.GenericActivity;
import com.garytokman.tokmangary_ce01.model.Person;

// Gary Tokman
// MDF3 - 1610
// DetailFragment

public class DetailFragment extends Fragment {

    public static final String ACTION_DELETE = "com.fullsail.android.ACTION_DELETE_DATA";

    private Person mPerson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_layout, container, false);

        // Get intent
        Intent intent = getActivity().getIntent();
        String firstName = intent.getStringExtra(GenericActivity.EXTRA_FIRST_NAME);
        String lastName = intent.getStringExtra(GenericActivity.EXTRA_LAST_NAME);
        int personAge = intent.getIntExtra(GenericActivity.EXTRA_AGE, 0);
        mPerson = new Person(firstName, lastName, personAge, getActivity());

        // Init
        TextView firstNameView = (TextView) view.findViewById(R.id.first_name_view);
        TextView lastNameView = (TextView) view.findViewById(R.id.last_name_view);
        TextView age = (TextView) view.findViewById(R.id.age_view);

        // Set
        firstNameView.setText(mPerson.getFirstName());
        lastNameView.setText(mPerson.getLastName());
        age.setText(mPerson.getAgeToString());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_action) {
            // Send delete broadcast with person
            Intent intent = new Intent(ACTION_DELETE);
            intent.putExtra(GenericActivity.EXTRA_FIRST_NAME, mPerson.getFirstName());
            intent.putExtra(GenericActivity.EXTRA_LAST_NAME, mPerson.getLastName());
            intent.putExtra(GenericActivity.EXTRA_AGE, mPerson.getAge());
            getActivity().sendBroadcast(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
