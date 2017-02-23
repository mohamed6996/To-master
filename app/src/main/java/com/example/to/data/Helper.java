package com.example.to.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.to.data.Contract.ToDoEntry;


public class Helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TODO.db";

    private static final int DATABASE_VERSION = 1;


    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + ToDoEntry.TABLE_NAME + " ("
                + ToDoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ToDoEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + ToDoEntry.COLUMN_DESCRIPTION + " TEXT, "
                + ToDoEntry.COLUMN_HOUR +" INTEGER, "
                + ToDoEntry.COLUMN_MINUTE +" INTEGER );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
