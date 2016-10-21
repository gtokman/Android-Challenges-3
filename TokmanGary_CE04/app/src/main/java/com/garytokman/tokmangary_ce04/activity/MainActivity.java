package com.garytokman.tokmangary_ce04.activity;

import android.Manifest.permission;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.garytokman.tokmangary_ce04.R;
import com.garytokman.tokmangary_ce04.fragments.PhotoMapFragment;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity {

    private static final String MAP_FRAGMENT = "MAP_FRAGMENT";
    private static final int REQUEST_CODE = 123;
    private static final String TAG  = MainActivity.class.getSimpleName();
    private PhotoMapFragment mMapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Map");

        boolean readStorage = canReadStorage();
        boolean readMaps = canReadMap();

        if (readMaps && readStorage) {
            // Add maps
            addFragment();
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        permission.READ_EXTERNAL_STORAGE,
                        permission.ACCESS_COARSE_LOCATION,
                        permission.ACCESS_FINE_LOCATION
                }, REQUEST_CODE);
            }
        }
    }

    private void addFragment() {
        mMapFragment = new PhotoMapFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, mMapFragment, MAP_FRAGMENT)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode != REQUEST_CODE) {
            return;
        }

        if (permissions[0].equals(permission.READ_EXTERNAL_STORAGE) && grantResults[0] == PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission good to read!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No permission for contacts", Toast.LENGTH_SHORT).show();
        }

        if (permissions[1].equals(permission.ACCESS_COARSE_LOCATION) && grantResults[1] == PERMISSION_GRANTED) {
            Toast.makeText(this, "Can access location!", Toast.LENGTH_SHORT).show();
            addFragment();
        } else Toast.makeText(this, "Cannot access location!", Toast.LENGTH_SHORT).show();
    }

    private boolean canReadStorage() {
        return ContextCompat.checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED;
    }

    private boolean canReadMap() {
        return ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED;
    }

}
