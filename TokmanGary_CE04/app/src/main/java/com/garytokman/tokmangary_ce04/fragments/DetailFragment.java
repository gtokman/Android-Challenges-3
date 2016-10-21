package com.garytokman.tokmangary_ce04.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garytokman.tokmangary_ce04.R;
import com.garytokman.tokmangary_ce04.database.DatabaseSchema;
import com.garytokman.tokmangary_ce04.database.PhotoDatabase;
import com.garytokman.tokmangary_ce04.model.Photo;

// Gary Tokman
// MDF3 - 1610
// DetailFragment

public class DetailFragment extends Fragment {

    public static final String EXTRA_PHOTO = "EXTRA_PHOTO";
    private Photo mPhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get intent
        Intent intent = getActivity().getIntent();
        mPhoto = (Photo) intent.getExtras().getSerializable(EXTRA_PHOTO);

        // Init
        TextView location = (TextView) view.findViewById(R.id.photoLocation);
        TextView name = (TextView) view.findViewById(R.id.photoName);
        ImageView image = (ImageView) view.findViewById(R.id.savedPhoto);

        if (mPhoto != null) {
            location.setText(mPhoto.getPhotoDesc());
            name.setText(mPhoto.getPhotoName());
            image.setImageURI(Uri.parse(mPhoto.getPhotoUri()));
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.deleteAction) {
            // TODO: Delete

            String where = DatabaseSchema.PhotoTable.Columns.PHOTO_URI + " = ?";
            String[] whereArgs = {mPhoto.getPhotoUri()};
            PhotoDatabase.getInstance(getActivity()).deletePhoto(where, whereArgs);

            getActivity().finish();
            return true;
        } else return super.onOptionsItemSelected(item);
    }
}
