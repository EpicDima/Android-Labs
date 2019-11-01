package com.dima.lab8.model;

import com.dima.lab8.model.db.CustomersCache;

import java.util.List;


public class CustomerList {
    private static CustomerList sCustomerList = null;

    private int mMaxSize;
    private CustomersCache mCustomersCache;

    private CustomerList(int maxSize) {
        this.mMaxSize = maxSize;
        this.mCustomersCache = new CustomersCache();
    }

    public static void createInstance(int size) {
        sCustomerList = new CustomerList(size);
    }

    public static void createInstance() {
        createInstance(0);
    }

    public static CustomerList getInstance() {
        return sCustomerList;
    }

    public void addCustomer(Customer customer) {
        mCustomersCache.addCustomer(customer);
    }

    public int getmMaxSize() {
        return mMaxSize;
    }

    public int getSize() {
        return mCustomersCache.getNumberOfCustomers();
    }

    public List<Customer> getCustomerArrayInAlphabeticalOrder() {
        return mCustomersCache.getCustomers();
    }

    public List<Customer> getCustomerArrayWithCardNumbersInRange(long begin, long end) {
        return mCustomersCache.getCustomersInCardNumberRange(begin, end);
    }

    public void deleteCustomers() {
        mCustomersCache.deleteCustomers();
    }
}
