package com.dima.lab2.model;

public class Customer {

    private int mId;
    private String mSurname;
    private String mName;
    private String middlename;
    private String mAddress;
    private long mCreditCardNumber;
    private long mBankAccountNumber;

    public Customer(int id, String surname, String name, String middlename, String address,
                    long creditCardNumber, long bankAccountNumber) {
        this.mId = id;
        this.mSurname = surname;
        this.mName = name;
        this.middlename = middlename;
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
        return middlename;
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
