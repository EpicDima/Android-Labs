package com.dima.lab4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final int SIGN_IN_REQUEST_CODE = 0;

    private GoogleApiClient mGoogleApiClient;

    private SignInButton mSignInButton;
    private Button mSignOutButton;
    private TextView mNameTextView;
    private TextView mEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignOutButton = findViewById(R.id.sign_out_button);
        mNameTextView = findViewById(R.id.displayname_textview);
        mEmailTextView = findViewById(R.id.email_textview);

        mSignInButton.setOnClickListener(this);
        mSignOutButton.setOnClickListener(this);

        mGoogleApiClient = buildGoogleApiClient();
    }

    private GoogleApiClient buildGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        if (!mGoogleApiClient.isConnecting()) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE);
                    break;
                case R.id.sign_out_button:
                    onSignedOut();
                    break;
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        OptionalPendingResult<GoogleSignInResult> opr
                = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

        opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
            @Override
            public void onResult(@NonNull GoogleSignInResult result) {
                if (result.isSuccess()) {
                    try {
                        mSignInButton.setEnabled(false);
                        mSignOutButton.setEnabled(true);
                        GoogleSignInAccount account = result.getSignInAccount();
                        if (account != null) {
                            mNameTextView.setText(String.format("Логин: %s",
                                    account.getDisplayName()));
                            mEmailTextView.setText(String.format("Email: %s",
                                    account.getEmail()));
                        }
                        mGoogleApiClient.disconnect();
                        mGoogleApiClient.connect();
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this, SIGN_IN_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }

    private void onSignedOut() {
        mSignOutButton.setEnabled(false);
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        mSignInButton.setEnabled(true);
                        mNameTextView.setText("");
                        mEmailTextView.setText("");
                        mGoogleApiClient.disconnect();
                        mGoogleApiClient.connect();
                    }
                });
    }
}
