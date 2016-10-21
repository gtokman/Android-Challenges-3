package com.garytokman.tokmangary_ce04.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.garytokman.tokmangary_ce04.R;
import com.garytokman.tokmangary_ce04.database.PhotoDatabase;
import com.garytokman.tokmangary_ce04.model.Photo;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;

// Gary Tokman
// MDF3 - 1610
// FormFragment

public class FormFragment extends Fragment {

    private static final int REQUEST_PHOTO = 2;
    private static final String TAG = FormFragment.class.getSimpleName();
    private File mPhotoFile;
    private ImageView mImageView;
    private EditText mLocationText;
    private EditText mImageNameText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPhotoFile = getPhotoFile();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        mImageView = (ImageView) view.findViewById(R.id.cameraImageView);
        mImageNameText = (EditText) view.findViewById(R.id.name_input);
        mLocationText = (EditText) view.findViewById(R.id.location_input);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = getActivity().getIntent();
        LatLng latLng = intent.getParcelableExtra(PhotoMapFragment.EXTRA_LOCATION);

        if (item.getItemId() == R.id.saveAction) {

            // Check if text fields are valid
            if (isEmptyText(mLocationText) || isEmptyText(mImageNameText)) {
                Toast.makeText(getActivity(), "No empty fields!", Toast.LENGTH_SHORT).show();
                return false;
            }

            // TODO: SAVE
            Photo photo = new Photo(getText(mLocationText),
                    getText(mImageNameText), mPhotoFile.getPath(),
                    latLng.latitude, latLng.longitude);

            PhotoDatabase.getInstance(getActivity()).savePhoto(photo);
            getActivity().finish();

            return true;
        } else if (item.getItemId() == R.id.cameraAction) {

            // Start camera intent
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = Uri.fromFile(mPhotoFile);
            if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(cameraIntent, REQUEST_PHOTO);
            }

            return true;
        } else return super.onOptionsItemSelected(item);
    }

    public File getPhotoFile() {
        File externalFilesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }

        return new File(externalFilesDir, getPhotoName());
    }

    public String getPhotoName() {
        return "img_" + System.currentTimeMillis() + ".jpg";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PHOTO) {

            Log.d(TAG, "onActivityResult: ");

            updateUI();
        }
    }

    private void updateUI() {
        if (!mPhotoFile.exists()) {
            mImageView.setImageBitmap(null);
            return;
        }

        // Set image
        Bitmap bitmap = getCameraImageBitmap();
        mImageView.setImageBitmap(bitmap);
    }

    private Bitmap getCameraImageBitmap() {
        // Read file
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mPhotoFile.getPath(), options);

        options = new BitmapFactory.Options();

        // Read and create bitmap
        return BitmapFactory.decodeFile(mPhotoFile.getPath(), options);
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }

    private boolean isEmptyText(EditText editText) {
        return getText(editText).isEmpty();
    }
}
