package com.garytokman.tokmangary_ce04.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.garytokman.tokmangary_ce04.R;
import com.garytokman.tokmangary_ce04.activity.DetailActivity;
import com.garytokman.tokmangary_ce04.activity.FormActivity;
import com.garytokman.tokmangary_ce04.database.CursorHelper;
import com.garytokman.tokmangary_ce04.database.PhotoDatabase;
import com.garytokman.tokmangary_ce04.model.Photo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Gary Tokman
// MDF3 - 1610
// PhotoMapFragment

public class PhotoMapFragment extends MapFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapLongClickListener {

    private static final String TAG = PhotoMapFragment.class.getSimpleName();
    public static final String EXTRA_LOCATION = "EXTRA_LOCATION";
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LatLng mLatLng;
    private MenuItem mItem;
    private final Map<String, Photo> mPhotoMap = new HashMap<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);

        // Set call back
        getMapAsync(this);

        // Init
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1000);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_menu, menu);

        mItem = menu.getItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.addAction) {
            startFormActivity(mLatLng);
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private void startFormActivity(LatLng latLng) {
        // Start form activity
        Intent intent = new Intent(getActivity(), FormActivity.class);
        intent.putExtra(EXTRA_LOCATION, latLng);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setInfoWindowAdapter(this);
        updateMap();
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

        updateMap();
    }

    private void updateMap() {
        if (mGoogleMap != null) {

            // Clear all markers
            mGoogleMap.clear();

            // Load saved data
            Cursor photoCursor = PhotoDatabase.getInstance(getActivity()).getPhotos();
            CursorHelper cursorHelper = new CursorHelper(photoCursor);
            List<Photo> photos = cursorHelper.getPhotos();

            // Show a single map marker for each data record saved
            // markers should be placed on the map using the latitude and longitude
            for (final Photo photo : photos) {
                // Set marker
                LatLng latLng = new LatLng(photo.getLat(), photo.getLong());
                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(photo.getPhotoName());
                mGoogleMap.addMarker(options);
                mGoogleMap.addMarker(options);
                mPhotoMap.put(mGoogleMap.addMarker(options).getTitle(), photo);

                Log.d(TAG, "onResume: " + mGoogleMap.addMarker(options).getTag());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mItem.setEnabled(true);
        Log.d(TAG, "onConnected: ");
        // When client connects
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // Get last cached location
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            // Request new // New location goes to callback
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            handleLocation(location);
        }
    }

    private void handleLocation(Location location) {
        Log.d(TAG, "handleLocation() called with: location = [" + location.toString() + "]");
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Init LatLng obj
        mLatLng = new LatLng(latitude, longitude);

        // Set marker
        MarkerOptions options = new MarkerOptions()
                .position(mLatLng);
        mGoogleMap.addMarker(options);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));

        // Stop receiving updates
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
        mItem.setEnabled(false);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: ");
        // TODO handle errors
    }

    @Override
    public void onLocationChanged(Location location) {
        // When location is updated
        handleLocation(location);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        // Inflate
        View view = getActivity().getLayoutInflater().inflate(R.layout.marker_layout, null, false);

        // Init
        TextView markerLocation = (TextView) view.findViewById(R.id.markerLocation);
        TextView markerPhotoName = (TextView) view.findViewById(R.id.photoName);
        ImageView imageView = (ImageView) view.findViewById(R.id.markerImage);

        Log.d(TAG, "getInfoContents: " + marker.getTitle());
        Photo photo = mPhotoMap.get(marker.getTitle());


        if (photo != null) {
            markerLocation.setText(photo.getPhotoDesc());
            markerPhotoName.setText(photo.getPhotoName());
            imageView.setImageURI(Uri.parse(photo.getPhotoUri()));
        }

        return view;

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Photo photo = mPhotoMap.get(marker.getTitle());
        if (photo != null) {

            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(DetailFragment.EXTRA_PHOTO, photo);
            startActivity(intent);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        startFormActivity(latLng);
    }
}
