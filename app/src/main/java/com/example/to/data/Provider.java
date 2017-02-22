package com.example.to.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.to.data.Contract.ToDoEntry;


public class Provider extends ContentProvider {

    Helper mHelper;

    public static final int TODO = 100;
    public static final int TODO_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.PATH, TODO);
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.PATH + "/#", TODO_WITH_ID);
    }


    @Override
    public boolean onCreate() {
        mHelper = new Helper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;
        switch (match) {
            case TODO:
                long id = db.insert(ToDoEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(ToDoEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("failed");
                }
                break;
            default:
                throw new UnsupportedOperationException("can not do this");
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    Cursor returnCursor;

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        SQLiteDatabase db = mHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match) {
            case TODO:
                returnCursor = db.query(ToDoEntry.TABLE_NAME, strings, s, strings1, null, null, null);
                break;
            case TODO_WITH_ID:
                break;
            default:
                throw new UnsupportedOperationException("can not do this");
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return returnCursor;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
