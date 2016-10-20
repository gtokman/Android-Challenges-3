package com.garytokman.tokmangary_ce04.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.garytokman.tokmangary_ce04.R;
import com.garytokman.tokmangary_ce04.activity.FormActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

// Gary Tokman
// MDF3 - 1610
// PhotoMapFragment

public class PhotoMapFragment extends MapFragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);

        getMapAsync(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.addAction) {
            // TODO: Show form
            Intent intent = new Intent(getActivity(), FormActivity.class);
            startActivity(intent);
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }
}
