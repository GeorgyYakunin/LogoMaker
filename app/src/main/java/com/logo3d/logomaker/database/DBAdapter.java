package com.logo3d.logomaker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBAdapter {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "Suitdb.sql";

    private static final String TABLE_NAME = "Change_fg_bg";
    private static final String ID = "id";
    private static final String FG_BG = "fg_bg";
    private static final String IMG_PATH = "image_path";

    private static final String TABLE_FAVOURITE ="Favourite_Images";

    private static final String KEY_ID = "Id";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_IMAGE_PATH = "image_path";

    private final Context context;
    public static DatabaseHelper DBHelper;
    private static SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public void saveData_Change_fg_bg(String fg_bg, String img_path) {
        try {

            db = DBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(FG_BG, fg_bg);
            values.put(IMG_PATH, img_path);


            db.insert(TABLE_NAME, null, values);
            db.close();

        } catch (Throwable t) {

        }
    }

    public Cursor getdata_Change_fg_bg(String f_b) {

        db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME + " WHERE " + FG_BG + " = " + f_b, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        close();

        return cursor;
    }

    public Cursor getdata() {

        String selectQuery = "SELECT  * FROM " + TABLE_NAME  ;

        db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


       if (cursor.moveToFirst()) {
            do {
            } while (cursor.moveToNext());
        }
        return cursor;
    }
    public void updatedata_Change_fg_bg(String fg_bg, String img_path) {

        try {
            db = DBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(FG_BG, fg_bg);
            values.put(IMG_PATH, img_path);


            db.update(TABLE_NAME, values, "fg_bg" + fg_bg, null);
            db.close();

        } catch (Throwable t) {

        }
    }

    public void saveMessageData(byte[] image_url, String image_path) {

        try {

            db = DBHelper.getReadableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_IMAGE_URL, image_url);
            values.put(KEY_IMAGE_PATH, image_path);

            db.insert(TABLE_FAVOURITE, null, values);

            db.close();

        } catch (Throwable t) {

        }
    }

    public Table_Favourite_Images_model getFavouritr(String image_path) {

        db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVOURITE, new String[]{KEY_ID, KEY_IMAGE_URL,KEY_IMAGE_PATH}, KEY_IMAGE_PATH + "=? ", new String[]{image_path}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Table_Favourite_Images_model bullets1_model = new Table_Favourite_Images_model(cursor.getInt(0), cursor.getBlob(1),cursor.getString(2));
        return bullets1_model;
    }

    public long GetRowCountofTable() {

        db = DBHelper.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM " + TABLE_FAVOURITE;
        SQLiteStatement statement = db.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        return count;
    }

/**/

    public String getSingleFavData(String name) {

        String data;
        db = DBHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVOURITE, new String[]{KEY_ID, KEY_IMAGE_URL,KEY_IMAGE_PATH}, KEY_IMAGE_PATH + "=? ", new String[]{name}, null, null, null, null);
        if (cursor.moveToFirst()) {

            data = cursor.getString(cursor.getColumnIndex(KEY_IMAGE_PATH));
        }else{
            data=null;
        }


        return data;
    }

    public void deleteDrawDetails(String image_path) {
        db = DBHelper.getReadableDatabase();

        try {

            db.delete(TABLE_FAVOURITE, KEY_IMAGE_PATH + " = ?", new String[]{image_path});

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cursor getFavData() {

        db = DBHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_FAVOURITE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }


    public void deleteFavData() {

        db = DBHelper.getReadableDatabase();
        try {

            db.delete(TABLE_FAVOURITE, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
