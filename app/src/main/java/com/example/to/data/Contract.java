package com.example.to.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lenovo on 2/20/2017.
 */

public final class Contract {
    private Contract() {
    }

    public static final String AUTHORITY = "com.example.to";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "todo";


    public static final class ToDoEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public final static String TABLE_NAME = "todo";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_HOUR = "hour";
        public final static String COLUMN_MINUTE = "minute";


    }

}