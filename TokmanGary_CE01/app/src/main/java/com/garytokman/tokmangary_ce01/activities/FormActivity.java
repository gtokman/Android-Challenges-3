package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// FromActivity

import android.app.Fragment;

import com.garytokman.tokmangary_ce01.fragments.FormFragment;

public class FormActivity extends GenericActivity {
    @Override
    public Fragment getFragment() {
        return new FormFragment();
    }
}
