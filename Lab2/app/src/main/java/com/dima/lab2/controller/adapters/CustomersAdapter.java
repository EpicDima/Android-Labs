package com.dima.lab2.controller.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dima.lab2.R;
import com.dima.lab2.model.Customer;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.CustomerViewHolder> {

    private Customer[] mCustomers;

    public CustomersAdapter(Customer[] customers) {
        this.mCustomers = customers;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.customer_item, viewGroup, false);
        return new CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomersAdapter.CustomerViewHolder holder, int position) {
        Customer customer = mCustomers[position];
        holder.idView.setText(String.valueOf(customer.getId()));
        holder.surnameView.setText(customer.getSurname());
        holder.nameView.setText(customer.getName());
        holder.middlenameView.setText(customer.getMiddlename());
        holder.addressView.setText(customer.getAddress());
        holder.creditCardNumberView.setText(String.valueOf(customer.getCreditCardNumber()));
        holder.bankAccountNumberView.setText(String.valueOf(customer.getBankAccountNumber()));
    }

    @Override
    public int getItemCount() {
        return mCustomers.length;
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView idView, surnameView, nameView, middlenameView,
                addressView, creditCardNumberView, bankAccountNumberView;

        CustomerViewHolder(@NonNull View view) {
            super(view);
            idView = view.findViewById(R.id.customerIdTextView);
            surnameView = view.findViewById(R.id.customerSurnameTextView);
            nameView = view.findViewById(R.id.customerNameTextView);
            middlenameView = view.findViewById(R.id.customerMiddlenameTextView);
            addressView = view.findViewById(R.id.customerAddressTextView);
            creditCardNumberView = view.findViewById(R.id.customerCreditCardNumberTextView);
            bankAccountNumberView = view.findViewById(R.id.customerBankAccountNumberTextView);
        }
    }
}
