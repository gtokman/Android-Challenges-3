package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// ListActivity

import android.app.Fragment;
import android.app.ListFragment;

public class ListActivity extends GenericActivity {
    @Override
    public Fragment getFragment() {
        return new ListFragment();
    }
}
