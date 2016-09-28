package com.garytokman.tokmangary_ce01.activities;
// Gary Tokman
// MDF3 - 1610
// GenericActivity

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.garytokman.tokmangary_ce01.R;

public abstract class GenericActivity extends AppCompatActivity {

    private static final String FRAGMENT = "FRAGMENT";
    public static final String ACTION_UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";
    public static final String ACTION_VIEW_DATA = "com.fullsail.android.ACTION_VIEW_DATA";
    public static final String EXTRA_FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    public static final String EXTRA_AGE = "com.fullsail.android.EXTRA_AGE";

    protected abstract Fragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        // Init layout with fragment
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, getFragment(), FRAGMENT)
                .commit();
    }
}
