package com.dima.lab8.model.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.dima.lab8.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersCache {

    private static final String GET_NUMBER_OF_CUSTOMERS = "SELECT COUNT(*) FROM " +
            DatabaseHelper.TABLE_CUSTOMERS;

    private static final String GET_CUSTOMERS_SQL = "SELECT * FROM " +
            DatabaseHelper.TABLE_CUSTOMERS + " ORDER BY " + DatabaseHelper.KEY_SURNAME;

    private static final String GET_CUSTOMERS_IN_CARD_NUMBER_RANGE_SQL = "SELECT * FROM " +
            DatabaseHelper.TABLE_CUSTOMERS + " WHERE " + DatabaseHelper.KEY_CREDIT_CARD_NUMBER +
            " >= ? AND " + DatabaseHelper.KEY_CREDIT_CARD_NUMBER + " <= ?";

    private static final String ADD_CUSTOMER_SQL = "INSERT OR REPLACE INTO " +
            DatabaseHelper.TABLE_CUSTOMERS +" (" +
            DatabaseHelper.KEY_ID + ", " + DatabaseHelper.KEY_SURNAME + ", " +
            DatabaseHelper.KEY_NAME + ", " + DatabaseHelper.KEY_MIDDLENAME + ", " +
            DatabaseHelper.KEY_ADDRESS + ", " + DatabaseHelper.KEY_CREDIT_CARD_NUMBER + ", " +
            DatabaseHelper.KEY_BANK_ACCOUNT_NUMBER + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_CUSTOMERS = "DELETE FROM " + DatabaseHelper.TABLE_CUSTOMERS;


    public int getNumberOfCustomers() {
        Cursor cursor = Database.getDatabase().rawQuery(GET_NUMBER_OF_CUSTOMERS, null);
        cursor.moveToFirst();
        int number = cursor.getInt(0);
        cursor.close();
        return number;
    }

    public List<Customer> getCustomers() {
        Cursor cursor = Database.getDatabase().rawQuery(GET_CUSTOMERS_SQL, null);
        return getCustomersByCursor(cursor);
    }

    public List<Customer> getCustomersInCardNumberRange(long begin, long end) {
        Cursor cursor = Database.getDatabase().rawQuery(GET_CUSTOMERS_IN_CARD_NUMBER_RANGE_SQL,
                new String[] {String.valueOf(begin), String.valueOf(end)});
        return getCustomersByCursor(cursor);
    }

    private List<Customer> getCustomersByCursor(Cursor cursor) {
        int indexId = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
        int indexSurname = cursor.getColumnIndex(DatabaseHelper.KEY_SURNAME);
        int indexName = cursor.getColumnIndex(DatabaseHelper.KEY_NAME);
        int indexMiddlename = cursor.getColumnIndex(DatabaseHelper.KEY_MIDDLENAME);
        int indexAddress = cursor.getColumnIndex(DatabaseHelper.KEY_ADDRESS);
        int indexCreditCardNumber = cursor.getColumnIndex(DatabaseHelper.KEY_CREDIT_CARD_NUMBER);
        int indexBankAccountNumber = cursor.getColumnIndex(DatabaseHelper.KEY_BANK_ACCOUNT_NUMBER);

        List<Customer> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                list.add(new Customer(cursor.getInt(indexId), cursor.getString(indexSurname),
                        cursor.getString(indexName), cursor.getString(indexMiddlename),
                        cursor.getString(indexAddress), cursor.getLong(indexCreditCardNumber),
                        cursor.getLong(indexBankAccountNumber)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public void addCustomer(Customer customer) {
        SQLiteStatement sqLiteStatement = Database.getDatabase().compileStatement(ADD_CUSTOMER_SQL);
        sqLiteStatement.bindLong(1, customer.getId());
        sqLiteStatement.bindString(2, customer.getSurname());
        sqLiteStatement.bindString(3, customer.getName());
        sqLiteStatement.bindString(4, customer.getMiddlename());
        sqLiteStatement.bindString(5, customer.getAddress());
        sqLiteStatement.bindLong(6, customer.getCreditCardNumber());
        sqLiteStatement.bindLong(7, customer.getBankAccountNumber());
        sqLiteStatement.execute();
    }


    public void deleteCustomers() {
        Database.getDatabase().execSQL(DELETE_CUSTOMERS);
    }
}
