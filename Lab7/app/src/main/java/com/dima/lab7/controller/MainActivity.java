package com.dima.lab7.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dima.lab7.R;
import com.dima.lab7.model.services.SaveService;
import com.dima.lab7.model.Customer;
import com.dima.lab7.model.CustomerList;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 94;
    private static final String CURRENT_SIZE_STRING = "Current size of array: ";

    private final Bank[] mBanks = {
            new Bank(52.41481045106871, 30.950411769683832, "Банк Дабрабыт"),
            new Bank(52.39978226246882, 30.913480858474717, "Беларусбанк"),
            new Bank(52.4072590718227, 30.915726999999986, "Приорбанк, ЦБУ 400/1"),
            new Bank(52.44407957177429, 31.001480499999996, "БелВЭБ"),
            new Bank(52.42550557178379, 30.994814499999972, "РРБ-Банк")
    };


    TextView mCurrentSizeTextView;
    TextView mNearestBankTextView;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MAIN", "onCreate");
        setContentView(R.layout.activity_main);
        bindButtons();

        mCurrentSizeTextView = findViewById(R.id.currentSizeOfArrayTextView);
        mNearestBankTextView = findViewById(R.id.nearestBankTextView);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        int size = CustomerList.readFile(this);
        String t = CURRENT_SIZE_STRING + size;
        mCurrentSizeTextView.setText(t);

        mLocationListener = createLocationListener();
        startService(new Intent(this, SaveService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MAIN", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MAIN", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MAIN", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MAIN", "onStop");
    }

    @Override
    protected void onDestroy() {
        Log.d("MAIN", "onDestroy");
        mLocationManager.removeUpdates(mLocationListener);
        stopService(new Intent(this, SaveService.class));
        super.onDestroy();
    }


    private void bindButtons() {
        Button inputButton = findViewById(R.id.inputCustomersButton);
        Button openFirstActivityButton = findViewById(R.id.firstTaskButton);
        Button openSecondActivityButton = findViewById(R.id.secondTaskButton);
        Button identifyNearestBankButton = findViewById(R.id.identifyNearestBankButton);

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

        identifyNearestBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MAIN", "click");
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSION_REQUEST_CODE);
                } else {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            10000, 0, mLocationListener);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MAIN", "yeees");
            } else {
                Log.d("MAIN", "noooo");
            }
        }
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

    private LocationListener createLocationListener() {
        return new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                showLocation(location);
            }

            @Override
            public void onProviderDisabled(String provider) {}

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("MAIN", "onProviderEnabled");
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    showLocation(mLocationManager.getLastKnownLocation(provider));
                }
            }

            @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
        };
    }

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)
                || location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
            int idx = -1;
            float min = Float.MAX_VALUE;
            for (int i = 0; i < mBanks.length; i++) {
                float t = mBanks[i].getLocation().distanceTo(location);
                if (t < min) {
                    min = t;
                    idx = i;
                }
            }
            String t = "Nearest bank: " + mBanks[idx].getName();
            mNearestBankTextView.setText(t);
        }
    }


    private class Bank {
        private Location location;
        private String name;

        Bank(double latitude, double longitude, String name) {
            this.location = new Location("");
            this.location.setLatitude(latitude);
            this.location.setLongitude(longitude);
            this.name = name;
        }

        Location getLocation() {
            return location;
        }

        String getName() {
            return name;
        }
    }
}
