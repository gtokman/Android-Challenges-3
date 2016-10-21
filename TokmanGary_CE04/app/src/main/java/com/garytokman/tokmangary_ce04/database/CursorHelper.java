package com.garytokman.tokmangary_ce04.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.garytokman.tokmangary_ce04.model.Photo;

import java.util.ArrayList;
import java.util.List;

// Gary Tokman
// MDF3 - 1610
// CursorHelper

public class CursorHelper extends CursorWrapper {

    public CursorHelper(Cursor cursor) {
        super(cursor);
    }

    public List<Photo> getPhotos() {

        List<Photo> photos = new ArrayList<>();

        while (moveToNext()) {

            String name = getString(getColumnIndex(DatabaseSchema.PhotoTable.Columns.PHOTO_NAME));
            String uri = getString(getColumnIndex(DatabaseSchema.PhotoTable.Columns.PHOTO_URI));
            String desc = getString(getColumnIndex(DatabaseSchema.PhotoTable.Columns.LOCATION_DESC));
            String lon = getString(getColumnIndex(DatabaseSchema.PhotoTable.Columns.LONGITUDE));
            String lat = getString(getColumnIndex(DatabaseSchema.PhotoTable.Columns.LATITUDE));

            photos.add(new Photo (desc, name, uri, Double.valueOf(lat), Double.valueOf(lon)));
        }

        return photos;
    }
}


