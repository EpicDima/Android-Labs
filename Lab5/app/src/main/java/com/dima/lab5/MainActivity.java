package com.dima.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mThemeEditText;
    private EditText mTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailEditText = findViewById(R.id.email_edittext);
        mThemeEditText = findViewById(R.id.theme_edittext);
        mTextEditText = findViewById(R.id.text_edittext);

        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEditFields();
            }
        });
    }

    private void send() {
        final String email = mEmailEditText.getText().toString().trim();
        final String theme = mThemeEditText.getText().toString().trim();
        final String text = mTextEditText.getText().toString().trim();
        if (!email.equals("") && !theme.equals("") && !text.equals("")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EmailSender emailSender = new EmailSender(email, theme);
                    emailSender.sendMessage(text);
                    onSuccess();
                }
            }).start();
        } else {
            Toast.makeText(this, "Пожалуйста заполните поля!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearEditFields() {
        mEmailEditText.setText("");
        mThemeEditText.setText("");
        mTextEditText.setText("");
    }

    private void onSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Письмо отправлено", Toast.LENGTH_SHORT).show();
                clearEditFields();
            }
        });
    }
}
