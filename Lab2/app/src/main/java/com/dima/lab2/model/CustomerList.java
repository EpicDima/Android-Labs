package com.dima.lab2.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CustomerList {

    private static CustomerList sCustomerList = null;

    private Customer[] mArray;
    private int mRecentlyAddedIndex = 0;

    private CustomerList(int maxSize) {
        this.mArray = new Customer[maxSize];
    }

    public static void createInstance(int size) {
        sCustomerList = new CustomerList(size);
    }

    public static CustomerList getInstance() {
        return sCustomerList;
    }

    public void addCustomer(Customer customer) {
        if (mRecentlyAddedIndex < mArray.length) {
            mArray[mRecentlyAddedIndex++] = customer;
        }
    }

    public int getRecentlyAddedIndex() {
        return mRecentlyAddedIndex;
    }

    public int getSize() {
        return mArray.length;
    }

    public Customer[] getCustomerArrayInAlphabeticalOrder() {
        Customer[] customers = Arrays.copyOf(mArray, mArray.length);
        Arrays.sort(customers, new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.getSurname().compareTo(o2.getSurname());
            }
        });
        return customers;
    }

    public Customer[] getCustomerArrayWithCardNumbersInRange(long begin, long end) {
        ArrayList<Customer> list = new ArrayList<>();
        for (Customer customer : mArray) {
            if (customer.getCreditCardNumber() >= begin && customer.getCreditCardNumber() <= end) {
                list.add(customer);
            }
        }
        Customer[] customers = new Customer[list.size()];
        customers = list.toArray(customers);
        return customers;
    }
}
