package com.garytokman.tokmangary_ce07.helper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.garytokman.tokmangary_ce07.database.DatabaseSchema.CarTable.Columns;
import com.garytokman.tokmangary_ce07.model.Car;

// Gary Tokman
// MDF3 - 1610
// CursorHelper

public class CursorHelper extends CursorWrapper {
    public CursorHelper(Cursor cursor) {
        super(cursor);
    }

    public Car getCar() {
        String make = getString(getColumnIndex(Columns.CAR_MAKE));
        String model = getString(getColumnIndex(Columns.CAR_MODEL));
        int year = getInt(getColumnIndex(Columns.YEAR));

        return new Car(make, model, year);
    }
}
