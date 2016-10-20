package com.garytokman.tokmangary_ce07.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.fragment.CarListFragment;

// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Car list");

        // Add frag
        getFragmentManager()
                .beginTransaction()
                .add(R.id.listContainer, new CarListFragment(), "FIST_FRAGMENT")
                .commit();
    }
}
