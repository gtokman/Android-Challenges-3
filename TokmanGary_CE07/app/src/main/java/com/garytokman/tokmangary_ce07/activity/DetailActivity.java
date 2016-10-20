package com.garytokman.tokmangary_ce07.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.fragment.DetailsFragment;

// Gary Tokman
// MDF3 - 1610
// DetailActivity

public class DetailActivity extends AppCompatActivity {

    private static final String DETAIL_FRAGMENT = "DETAIL_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("Car details");

        // Add fragment
        getFragmentManager()
                .beginTransaction()
                .add(R.id.detailContainer, new DetailsFragment(), DETAIL_FRAGMENT)
                .commit();
    }
}
