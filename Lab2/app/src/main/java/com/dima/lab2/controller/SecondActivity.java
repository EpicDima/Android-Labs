package com.dima.lab2.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dima.lab2.controller.adapters.CustomersAdapter;
import com.dima.lab2.R;
import com.dima.lab2.model.CustomerList;


public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        openInputRangeDialog();
    }

    private void setDataForRecyclerView(View itemView) {
        try {
            long begin = Long.valueOf(((EditText)
                    itemView.findViewById(R.id.beginEditText)).getText().toString());
            long end = Long.valueOf(((EditText)
                    itemView.findViewById(R.id.endEditText)).getText().toString());
            RecyclerView recyclerView = findViewById(R.id.customersRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new CustomersAdapter(CustomerList.getInstance()
                    .getCustomerArrayWithCardNumbersInRange(begin, end)));
        } catch (NumberFormatException ex) {
            Toast.makeText(this, "Input fields must be filled in!", Toast.LENGTH_SHORT).show();
            openInputRangeDialog();
        }

    }

    private void openInputRangeDialog() {
        createInputRangeDialog().show();
    }

    @SuppressLint("InflateParams")
    private AlertDialog createInputRangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.input_range_dialog, null);
        builder.setView(view)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        setDataForRecyclerView(view);
                    }
                }).setCancelable(false);
        return builder.create();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
