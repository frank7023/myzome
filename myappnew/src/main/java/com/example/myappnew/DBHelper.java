package com.example.myappnew;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 7023 on 2016/3/29.
 */
public class DBHelper extends SQLiteOpenHelper{
    public static final String FOODNAME = "NAME";
    public DBHelper(Context context) {
        super(context,"collect.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + FOODNAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
