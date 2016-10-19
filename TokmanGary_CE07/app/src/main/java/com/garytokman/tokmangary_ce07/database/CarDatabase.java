package com.garytokman.tokmangary_ce07.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.garytokman.tokmangary_ce07.database.DatabaseSchema.CarTable.Columns;
import com.garytokman.tokmangary_ce07.model.Car;

// Gary Tokman
// MDF3 - 1610
// CarDatabase

public class CarDatabase extends SQLiteOpenHelper {
    private static CarDatabase ourInstance;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CarsDatabase.db";

    public static CarDatabase getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new CarDatabase(context);
        }
        return ourInstance;
    }

    private static final String CREATE_TABLE = "create table if not exists " +
            DatabaseSchema.CarTable.NAME + "(" +
            Columns.ID + " integer primary key autoincrement, " +
            Columns.CAR_MAKE + " text, " +
            Columns.CAR_MODEL + " text, " +
            Columns.YEAR + " integer)";


    private CarDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getCars() {
        return getReadableDatabase().query(DatabaseSchema.CarTable.NAME, null, null, null, null, null, null);
    }

    public void deleteCar(String where, String[] whereArgs) {
        getWritableDatabase().delete(DatabaseSchema.CarTable.NAME, where, whereArgs);
    }

    public void saveCar(Car car) {
        getWritableDatabase().insert(DatabaseSchema.CarTable.NAME, null, getValues(car));
    }

    private ContentValues getValues(Car car) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Columns.CAR_MAKE, car.getMake());
        contentValues.put(Columns.CAR_MODEL, car.getModel());
        contentValues.put(Columns.YEAR, car.getYear());

        return contentValues;
    }

}
