/*package com.example.noteapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AdminDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "admin.db";
    public static final int DB_VERSION = 1;
    public static final String TB_NAME = "Admin";
    private static String DB_PATH = "";
    static Context context;
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASS = "password";

    SQLiteDatabase db;

    public AdminDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (checkDB != null)
            checkDB.close();

        return checkDB != null;
    }

    private void copyDataBase() throws IOException {
        InputStream inputStream = context.getAssets().open(DB_NAME);
        File file = context.getDatabasePath(DB_NAME);
        String string = file.getPath();
        OutputStream outputStream = new FileOutputStream(string);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDatabase() {
        File file = context.getDatabasePath(DB_NAME);
        String string = file.getPath();
        db = SQLiteDatabase.openDatabase(string, null, SQLiteDatabase.OPEN_READONLY);
    }

    public boolean ReadFromDb(String userName, String passWord) {

        String name = "";
        String password = "";
        db = this.getWritableDatabase();
        Cursor cursor = db.query(TB_NAME,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
            password = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASS));
            if (String.valueOf(userName).equals(name) && String.valueOf(passWord).equals(password)) {
                return true;

            }
        }
        return true;
    }
}
*/