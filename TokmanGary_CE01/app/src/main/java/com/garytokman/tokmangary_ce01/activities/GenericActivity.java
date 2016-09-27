package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// GenericActivity

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.garytokman.tokmangary_ce01.R;

public abstract class GenericActivity extends AppCompatActivity {

    public abstract Fragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        // Init layout with fragment
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, getFragment(), "FRAGMENT")
                .addToBackStack(null)
                .commit();
    }
}
