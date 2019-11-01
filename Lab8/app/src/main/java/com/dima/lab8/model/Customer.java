package com.dima.lab8.model;

import java.io.Serializable;

public class Customer implements Serializable {

    private int mId;
    private String mSurname;
    private String mName;
    private String mMiddlename;
    private String mAddress;
    private long mCreditCardNumber;
    private long mBankAccountNumber;

    public Customer(int id, String surname, String name, String middlename, String address,
                    long creditCardNumber, long bankAccountNumber) {
        this.mId = id;
        this.mSurname = surname;
        this.mName = name;
        this.mMiddlename = middlename;
        this.mAddress = address;
        this.mCreditCardNumber = creditCardNumber;
        this.mBankAccountNumber = bankAccountNumber;
    }

    public int getId() {
        return mId;
    }

    public String getSurname() {
        return mSurname;
    }

    public String getName() {
        return mName;
    }

    public String getMiddlename() {
        return mMiddlename;
    }

    public String getAddress() {
        return mAddress;
    }

    public long getCreditCardNumber() {
        return mCreditCardNumber;
    }

    public long getBankAccountNumber() {
        return mBankAccountNumber;
    }
}
