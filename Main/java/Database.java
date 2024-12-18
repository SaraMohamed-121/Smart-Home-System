package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String databaseName = "myDatabase";
    public Database(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (name TEXT, username TEXT, email TEXT primary key, password TEXT, birthDate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void addUser(String name, String username, String email, String password, String birthDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name", name);
        row.put("username", username);
        row.put("email", email);
        row.put("password", password);
        row.put("birthDate", birthDate);

        db.insert("users", null, row);
        db.close();
    }

    public String getName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String []{email});
        String name = "";
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return name;
    }

    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String []{email});
        String username = "";
        if (cursor.moveToFirst()) {
            do {
                username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return username;
    }

    public Boolean checkCredentials(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        String password_db = null;

        if (cursor != null){
            if (cursor.moveToFirst()) {
                password_db = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            }
        }
        return password.equals(password_db);
    }

}
