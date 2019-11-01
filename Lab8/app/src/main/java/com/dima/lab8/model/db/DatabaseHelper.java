package com.dima.lab8.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public final class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lab8_db";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_CUSTOMERS = "customers";
    static final String KEY_ID = "id";
    static final String KEY_SURNAME = "surname";
    static final String KEY_NAME = "name";
    static final String KEY_MIDDLENAME = "middlename";
    static final String KEY_ADDRESS = "address";
    static final String KEY_CREDIT_CARD_NUMBER = "creditCardNumber";
    static final String KEY_BANK_ACCOUNT_NUMBER = "bankAccountNumber";

    private static final String CREATE_CUSTOMERS_SQL = "CREATE TABLE " + TABLE_CUSTOMERS + " ("
            + KEY_ID + " INTEGER, " + KEY_SURNAME + " TEXT, " + KEY_NAME + " TEXT, "
            + KEY_MIDDLENAME + " TEXT, " + KEY_ADDRESS + " TEXT, "
            + KEY_CREDIT_CARD_NUMBER + " INTEGER, " + KEY_BANK_ACCOUNT_NUMBER + " INTEGER)";

    private static final String DROP_CUSTOMERS_SQL = "DROP TABLE IF EXISTS " + TABLE_CUSTOMERS;

    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMERS_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CUSTOMERS_SQL);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
