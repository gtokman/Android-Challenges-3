package com.garytokman.tokmangary_ce03.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.garytokman.tokmangary_ce03.database.DatabaseSchema.PersonTable;
import com.garytokman.tokmangary_ce03.database.DatabaseSchema.PersonTable.Columns;
import com.garytokman.tokmangary_ce03.model.Article;

// Gary Tokman
// MDF3 - 1610
// ArticleDatabase

public class ArticleDatabase extends SQLiteOpenHelper {

    private static ArticleDatabase sArticleDatabase;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ArticlesProvider.db";
    private static final String CREATE_TABLE = "create table if not exists " +
            DatabaseSchema.PersonTable.NAME + "(" +
            Columns.ID + " integer primary key autoincrement, " +
            Columns.AUTHOR + " text, " +
            Columns.DESC + " text, " +
            Columns.TITLE + " text, " +
            Columns.URL + " text, " +
            Columns.URL_IMAGE + " text " + ")";

    private ArticleDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static ArticleDatabase newInstance(Context context) {
        if (sArticleDatabase == null) {
            sArticleDatabase = new ArticleDatabase(context);
        }

        return sArticleDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getArticle() {
        return getReadableDatabase().query(PersonTable.NAME, null, null, null, null, null, null);
    }

    public void saveArticle(Article article) {
        getWritableDatabase().insert(PersonTable.NAME, null, getValues(article));
    }

    private ContentValues getValues(Article article) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Columns.AUTHOR, article.getAuthor());
        contentValues.put(Columns.TITLE, article.getTitle());
        contentValues.put(Columns.DESC, article.getDescription());
        contentValues.put(Columns.URL, article.getUrl());
        contentValues.put(Columns.URL_IMAGE, article.getUrlToImage());

        return contentValues;
    }
}
