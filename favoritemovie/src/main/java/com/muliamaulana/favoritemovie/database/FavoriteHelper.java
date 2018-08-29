package com.muliamaulana.favoritemovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.muliamaulana.favoritemovie.provider.FavoriteColumns;

import static android.provider.BaseColumns._ID;

/**
 * Created by muliamaulana on 02/04/18.
 */

public class FavoriteHelper {
    private static String TABLE_NAME = FavoriteColumns.TABLE_NAME;

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    public FavoriteHelper(Context mContext) {
        this.mContext = mContext;
    }

    public FavoriteHelper open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDatabaseHelper.close();
    }

    public Cursor queryProvider(){
        return mDatabase.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,_ID+ " DESC");
    }

    public Cursor queryByIdProvider (String id){
        return mDatabase.query(TABLE_NAME, null,_ID+" = ?", new String[]{id},null,null,null);
    }

    public long insertProvider(ContentValues contentValues){
        return mDatabase.insert(TABLE_NAME,null,contentValues);
    }

    public int updateProvider(String id, ContentValues contentValues){
        return mDatabase.update(TABLE_NAME, contentValues, _ID+ " = ?", new String[]{id});
    }
    public int deleteProvider (String id){
        return mDatabase.delete(TABLE_NAME, _ID+ " = ?", new String[]{id});
    }
}
