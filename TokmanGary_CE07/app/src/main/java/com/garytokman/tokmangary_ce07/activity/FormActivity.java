package com.garytokman.tokmangary_ce07.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.garytokman.tokmangary_ce07.fragment.FormFragment;
import com.garytokman.tokmangary_ce07.R;

// Gary Tokman
// MDF3 - 1610
// FormActivity

public class FormActivity extends AppCompatActivity {

    private static final String FORM_FRAGMENT = "FORM_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        setTitle("Add a new car");

        // Add frag
        getFragmentManager()
                .beginTransaction()
                .add(R.id.formContainer, new FormFragment(), FORM_FRAGMENT)
                .commit();
    }
}
