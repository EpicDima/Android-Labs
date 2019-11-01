package com.dima.lab7.model;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomerList {

    private static final String FILENAME = "file.txt";

    private static CustomerList sCustomerList = null;

    private List<Customer> mCustomers;
    private int mRecentlyAddedIndex = 0;

    private CustomerList(int maxSize) {
        this.mCustomers = new ArrayList<>(maxSize);
    }

    public static void createInstance(int size) {
        sCustomerList = new CustomerList(size);
    }

    public static CustomerList getInstance() {
        return sCustomerList;
    }

    public void addCustomer(Customer customer) {
        if (mRecentlyAddedIndex < mCustomers.size()) {
            mCustomers.set(mRecentlyAddedIndex++, customer);
        }
    }

    private List<Customer> getArray() {
        return mCustomers;
    }

    public int getRecentlyAddedIndex() {
        return mRecentlyAddedIndex;
    }

    public int getSize() {
        return mCustomers.size();
    }

    public List<Customer> getCustomerArrayInAlphabeticalOrder() {
        List<Customer> customers = new ArrayList<>(mCustomers);
        Collections.sort(customers, new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.getSurname().compareTo(o2.getSurname());
            }
        });
        return customers;
    }

    public List<Customer> getCustomerArrayWithCardNumbersInRange(long begin, long end) {
        List<Customer> customers = new ArrayList<>();
        for (Customer customer : mCustomers) {
            if (customer.getCreditCardNumber() >= begin && customer.getCreditCardNumber() <= end) {
                customers.add(customer);
            }
        }
        return customers;
    }

    public static void writeFile(Context context) {
        if (CustomerList.getInstance() == null) {
            return;
        }
        int size = CustomerList.getInstance().getSize();
        if (size > 0) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(context
                        .openFileOutput(FILENAME, Context.MODE_PRIVATE));
                objectOutputStream.writeInt(CustomerList.getInstance().getSize());
                List<Customer> array = CustomerList.getInstance().getArray();
                for (int i = 0; i < size; i++) {
                    objectOutputStream.writeObject(array.get(i));
                }
                objectOutputStream.close();
            } catch (IOException ex) {
                Log.d("MAIN", ex.getMessage());
            }
        }
    }

    public static int readFile(Context context) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(FILENAME));
            int size = objectInputStream.readInt();
            CustomerList.createInstance(size);
            for (int i = 0; i < size; i++) {
                CustomerList.getInstance().addCustomer((Customer) objectInputStream.readObject());
            }
            return size;
        } catch (IOException | ClassNotFoundException ex) {
            Log.d("MAIN", ex.getMessage() == null ? "" : ex.getMessage());
            return 0;
        }
    }
}
