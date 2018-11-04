package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Signup_1Page extends AppCompatActivity {
    private String Email, Password, PasswordReendter, Name;
    public final static String EMAIL = "com.csc436.jz.sportsgroupup.EMAIL";
    public final static String PASSWORD = "com.csc436.jz.sportsgroupup.PASSWORD";
    public final static String NAME = "com.csc436.jz.sportsgroupup.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_1_page);

        Button signUpNext = findViewById(R.id.signup_Next);
        Button signUpBack = findViewById(R.id.signup_back);

        signUpNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.signup_email);
                EditText password = findViewById(R.id.signup_password_1);
                EditText passwordReenter = findViewById(R.id.signup_password_2);
                EditText name = findViewById(R.id.signup_name);

                Email = email.getText().toString();
                Password = password.getText().toString();
                PasswordReendter = passwordReenter.getText().toString();
                Name = name.getText().toString();

                if (Password.compareTo(PasswordReendter) != 0) {

                } else {

                    // hashing passward


                    Intent intent = new Intent(Signup_1Page.this, Signup_2Page.class);
                    intent.putExtra(EMAIL, Email);
                    intent.putExtra(PASSWORD, Password);
                    intent.putExtra(NAME, Name);
                    startActivity(intent);
                }
            }
        });

        signUpBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

    }

}
