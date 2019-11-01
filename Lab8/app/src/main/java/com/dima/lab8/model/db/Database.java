package com.dima.lab8.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public final class Database {

    private static DatabaseHelper sDbHelper;
    private static SQLiteDatabase sSqLiteDatabase;

    private Database() {}

    public static void initDatabase(Context context) {
        if (sDbHelper == null) {
            sDbHelper = new DatabaseHelper(context);
        }
        getDatabase();
    }

    static SQLiteDatabase getDatabase() {
        if (sSqLiteDatabase == null || sSqLiteDatabase.isReadOnly()) {
            sSqLiteDatabase = sDbHelper.getWritableDatabase();
        }
        return sSqLiteDatabase;
    }

    public static DatabaseHelper getHelper() {
        return sDbHelper;
    }

    public static void closeDatabase() {
        sDbHelper = null;
        sSqLiteDatabase.close();
        sSqLiteDatabase = null;
    }
}
