package com.garytokman.tokmangary_ce04.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.garytokman.tokmangary_ce04.R;
import com.garytokman.tokmangary_ce04.fragments.PhotoMapFragment;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.os.Build.VERSION.SDK_INT;


// Gary Tokman
// MDF3 - 1610
// MainActivity

public class MainActivity extends AppCompatActivity {

    private static final String MAP_FRAGMENT = "MAP_FRAGMENT";
    private static final int REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SDK_INT >= Build.VERSION_CODES.M && !canReadStorage()) {
            // Read contacts
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            addFragment();
        }


    }

    private void addFragment() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, new PhotoMapFragment(), MAP_FRAGMENT)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE && grantResults[0] == PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission good!", Toast.LENGTH_SHORT).show();
            addFragment();
        } else {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean canReadStorage() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED;
    }

}
