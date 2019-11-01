package com.dima.lab2.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dima.lab2.R;
import com.dima.lab2.model.Customer;
import com.dima.lab2.model.CustomerList;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_SIZE_STRING = "Current size of array: ";

    private TextView mCurrentSizeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindButtons();

        mCurrentSizeTextView = findViewById(R.id.currentSizeOfArrayTextView);
        String t = CURRENT_SIZE_STRING + "0";
        mCurrentSizeTextView.setText(t);
    }

    private void bindButtons() {
        Button inputButton = findViewById(R.id.inputCustomersButton);
        Button openFirstActivityButton = findViewById(R.id.firstTaskButton);
        Button openSecondActivityButton = findViewById(R.id.secondTaskButton);

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.openInputArraySizeDialog();
            }
        });

        openFirstActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CustomerList.getInstance() != null) {
                    startActivity(new Intent(MainActivity.this, FirstActivity.class));
                } else {
                    Toast.makeText(v.getContext(), "No data available!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        openSecondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CustomerList.getInstance() != null) {
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                } else {
                    Toast.makeText(v.getContext(), "No data available!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void openInputArraySizeDialog() {
        createInputArraySizeDialog().show();
    }

    @SuppressLint("InflateParams")
    private AlertDialog createInputArraySizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.input_array_size_dialog, null);
        builder.setView(view)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        acceptArraySizeDialog(view);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false);
        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    private void acceptArraySizeDialog(View view) {
        EditText sizeOfArrayEditText = view.findViewById(R.id.sizeOfArrayEditText);
        String s = sizeOfArrayEditText.getText().toString();
        if (!s.isEmpty()) {
            int size = Integer.valueOf(s);
            if (size > 0) {
                CustomerList.createInstance(size);
                String t = CURRENT_SIZE_STRING + size;
                mCurrentSizeTextView.setText(t);
                openInputCustomerDialog();
            } else {
                Toast.makeText(getApplicationContext(), "Size must be positive!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "The input field must be filled in!",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void openInputCustomerDialog() {
        AlertDialog dialog = createInputCustomerDialog();
        dialog.show();
    }

    @SuppressLint("InflateParams")
    private AlertDialog createInputCustomerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.input_customer_dialog, null);
        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addCustomer(view);
                        if (CustomerList.getInstance().getSize() != CustomerList.getInstance().getRecentlyAddedIndex()) {
                            openInputCustomerDialog();
                        }
                    }
                }).setCancelable(false);
        return builder.create();
    }

    private void addCustomer(View view) {
        String customerSurname = ((EditText)
                view.findViewById(R.id.customerSurnameEditText)).getText().toString();
        String customerName = ((EditText)
                view.findViewById(R.id.customerNameEditText)).getText().toString();
        String customerMiddlename = ((EditText)
                view.findViewById(R.id.customerMiddlenameEditText)).getText().toString();
        String customerAddress = ((EditText)
                view.findViewById(R.id.customerAddressEditText)).getText().toString();
        try {
            int customerId = Integer.valueOf(((EditText)
                    view.findViewById(R.id.customerIdEditText)).getText().toString());
            long customerCreditCardNumber = Long.valueOf(((EditText)
                    view.findViewById(R.id.customerCreditCardNumberEditText)).getText().toString());
            long customerBankAccountNumber = Long.valueOf(((EditText)
                    view.findViewById(R.id.customerBankAccountNumberEditText)).getText().toString());
            CustomerList.getInstance().addCustomer(new Customer(customerId, customerSurname,
                    customerName, customerMiddlename, customerAddress, customerCreditCardNumber,
                    customerBankAccountNumber));
        } catch (NumberFormatException ex) {
            Toast.makeText(this, "Input fields must be filled in!", Toast.LENGTH_SHORT).show();
        }
    }
}
