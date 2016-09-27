package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// ListActivity

import android.app.Fragment;

import com.garytokman.tokmangary_ce01.R;
import com.garytokman.tokmangary_ce01.fragments.PersonListFragment;

public class ListActivity extends GenericActivity {
    @Override
    public Fragment getFragment() {
        return new PersonListFragment();
    }

    @Override
    protected int getMenu() {
        return R.menu.list_menu;
    }
}
