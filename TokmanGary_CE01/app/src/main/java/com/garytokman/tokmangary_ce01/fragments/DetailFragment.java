package com.garytokman.tokmangary_ce01.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.garytokman.tokmangary_ce01.R;

// Gary Tokman
// MDF3 - 1610
// DetailFragment

public class DetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_layout, container, false);

        // Get intent
        Intent intent = getActivity().getIntent();
        String firstName = intent.getStringExtra(PersonListFragment.EXTRA_FIRST_NAME);
        String lastName = intent.getStringExtra(PersonListFragment.EXTRA_LAST_NAME);
        int personAge = intent.getIntExtra(PersonListFragment.EXTRA_AGE, 0);

        // Init
        TextView firstNameView = (TextView) view.findViewById(R.id.first_name_view);
        TextView lastNameView = (TextView) view.findViewById(R.id.last_name_view);
        TextView age = (TextView) view.findViewById(R.id.age_view);

        // Set
        firstNameView.setText(firstName);
        lastNameView.setText(lastName);
        age.setText(String.valueOf(personAge));

        return view;
    }
}
