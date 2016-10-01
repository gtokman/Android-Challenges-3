package com.garytokman.tokmangary_ce02;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.garytokman.tokmangary_ce02.fragment.GridViewFragment;

// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity {

    private static final String GRIDVIEW_FRAGMENT = "GRIDVIEW_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.container, new GridViewFragment(), GRIDVIEW_FRAGMENT)
                .commit();
    }
}
