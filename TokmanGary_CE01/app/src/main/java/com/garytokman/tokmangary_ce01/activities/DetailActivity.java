package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// DetailActivity

import android.app.Fragment;

import com.garytokman.tokmangary_ce01.fragments.DetailFragment;

public class DetailActivity extends GenericActivity {
    @Override
    public Fragment getFragment() {
        return new DetailFragment();
    }
}
