package com.garytokman.tokmangary_ce04.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.garytokman.tokmangary_ce04.database.DatabaseSchema.PhotoTable;
import com.garytokman.tokmangary_ce04.database.DatabaseSchema.PhotoTable.Columns;
import com.garytokman.tokmangary_ce04.model.Photo;

// Gary Tokman
// MDF3 - 1610
// PhotoDatabase

public class PhotoDatabase extends SQLiteOpenHelper {

    private static PhotoDatabase ourInstance;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CarsDatabase.db";

    public static PhotoDatabase getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new PhotoDatabase(context);
        }
        return ourInstance;
    }

    private static final String CREATE_TABLE = "create table if not exists " +
            PhotoTable.NAME + "(" +
            Columns.ID + " integer primary key autoincrement, " +
            Columns.LOCATION_DESC + " text, " +
            Columns.PHOTO_NAME + " text, " +
            Columns.PHOTO_URI + " text, " +
            Columns.LATITUDE + " text, " +
            Columns.LONGITUDE + " text)";


    private PhotoDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getPhotos() {
        return getReadableDatabase().query(DatabaseSchema.PhotoTable.NAME, null, null, null, null, null, null);
    }

    public void deletePhoto(String where, String[] whereArgs) {
        getWritableDatabase().delete(DatabaseSchema.PhotoTable.NAME, where, whereArgs);
    }

    public void savePhoto(Photo photo) {
        getWritableDatabase().insert(DatabaseSchema.PhotoTable.NAME, null, getValues(photo));
    }

    private ContentValues getValues(Photo photo) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Columns.PHOTO_NAME, photo.getPhotoName());
        contentValues.put(Columns.PHOTO_URI, photo.getPhotoUri());
        contentValues.put(Columns.LOCATION_DESC, photo.getPhotoDesc());
        contentValues.put(Columns.LATITUDE, photo.getLatToString());
        contentValues.put(Columns.LONGITUDE, photo.getLongToString());

        return contentValues;
    }
}
