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

        Intent intent = getActivity().getIntent();
        String personName = intent.getStringExtra(Intent.EXTRA_TEXT);
        int personAge = intent.getIntExtra(Intent.EXTRA_PHONE_NUMBER, 0);

        // Init
        TextView fullName = (TextView) view.findViewById(R.id.full_name_view);
        TextView age = (TextView) view.findViewById(R.id.age_view);

        // Set
        fullName.setText(personName);
        age.setText(String.valueOf(personAge));

        return view;
    }
}
