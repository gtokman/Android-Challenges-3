package com.garytokman.tokmangary_ce01.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.garytokman.tokmangary_ce01.R;

// Gary Tokman
// MDF3 - 1610
// FormFragment

public class FormFragment extends Fragment {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mAge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.form_layout, container, false);

        // Init
        mFirstName = (EditText) view.findViewById(R.id.first_name_text);
        mLastName = (EditText) view.findViewById(R.id.last_name_text);
        mAge = (EditText) view.findViewById(R.id.age_text);

        return view;
    }
}
