package com.garytokman.tokmangary_ce01.datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.garytokman.tokmangary_ce01.datebase.DatabaseSchema.PersonTable;
import com.garytokman.tokmangary_ce01.datebase.DatabaseSchema.PersonTable.Columns;
import com.garytokman.tokmangary_ce01.model.Person;

// Gary Tokman
// MDF3 - 1610
// PersonDatabase

public class PersonDatabase extends SQLiteOpenHelper {

    private static PersonDatabase sPersonDatabase;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ArticlesProvider.db";
    private static final String CREATE_TABLE = "create table if not exists " +
            DatabaseSchema.PersonTable.NAME + "(" +
            Columns.ID + " integer primary key autoincrement, " +
            Columns.FIRST_NAME + " text, " +
            Columns.LAST_NAME + " text, " +
            Columns.AGE + " integer " + ")";

    private PersonDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static PersonDatabase getInstance(Context context) {

        if (sPersonDatabase == null) {
            sPersonDatabase = new PersonDatabase(context);
        }

        return sPersonDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getPeople() {
        return getReadableDatabase().query(PersonTable.NAME, null, null, null, null, null, null);
    }

    public void deletePerson(String where, String[] whereArgs) {
        getWritableDatabase().delete(PersonTable.NAME, where, whereArgs);
    }

    public void savePerson(Person person) {
        getWritableDatabase().insert(PersonTable.NAME, null, getValues(person));
    }

    private ContentValues getValues(Person person) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Columns.FIRST_NAME, person.getFirstName());
        contentValues.put(Columns.LAST_NAME, person.getLastName());
        contentValues.put(Columns.AGE, person.getAge());

        return contentValues;
    }
}
