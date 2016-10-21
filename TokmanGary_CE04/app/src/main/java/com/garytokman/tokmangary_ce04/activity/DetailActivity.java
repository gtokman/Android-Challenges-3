package com.garytokman.tokmangary_ce04.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.garytokman.tokmangary_ce04.R;
import com.garytokman.tokmangary_ce04.fragments.DetailFragment;

// Gary Tokman
// MDF3 - 1610
// DetailActivity

public class DetailActivity extends AppCompatActivity {

    private static final String DETAIL_FRAGMENT = "DETAIL_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("Photo Details");

        getFragmentManager()
                .beginTransaction()
                .add(R.id.detailContainer, new DetailFragment(), DETAIL_FRAGMENT)
                .commit();
    }
}
