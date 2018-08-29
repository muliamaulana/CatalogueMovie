package com.muliamaulana.favoritemovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.muliamaulana.favoritemovie.provider.FavoriteColumns;

/**
 * Created by muliamaulana on 02/04/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "catalogue_movie";
    public static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MOVIE = "create table " + FavoriteColumns.TABLE_NAME + " (" +
                FavoriteColumns.COLUMN_ID + " integer primary key autoincrement, " +
                FavoriteColumns.COLUMN_TITLE + " text not null, " +
                FavoriteColumns.COLUMN_BACKDROP + " text not null, " +
                FavoriteColumns.COLUMN_POSTER + " text not null, " +
                FavoriteColumns.COLUMN_RELEASE_DATE + " text not null, " +
                FavoriteColumns.COLUMN_VOTE + " text not null, " +
                FavoriteColumns.COLUMN_OVERVIEW + " text not null " +
                ");";

        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE MOVIE IF EXISTS " + FavoriteColumns.TABLE_NAME);
        onCreate(db);
    }
}

