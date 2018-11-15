package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csc436.jz.sportsgroupup.Tools.Password_Hash;

public class Signup_1Page extends AppCompatActivity {
    private String Email, Password, PasswordReenter, Name;
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
                PasswordReenter = passwordReenter.getText().toString();
                Name = name.getText().toString();

                if(Email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter an email address to register", Toast.LENGTH_LONG).show();
                } else if (Password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Don't forget enter a password", Toast.LENGTH_LONG).show();
                }  else if ( !Password.equals(PasswordReenter) ) {
                    Toast.makeText(getApplicationContext(), "Two passwords don't match", Toast.LENGTH_LONG).show();
                } else if (Name.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
                } else if( !checkEmail()) {
                    Toast.makeText(getApplicationContext(), "Please enter your Catmail", Toast.LENGTH_LONG).show();
                } else {

                    Password = Password_Hash.SHA1(Password);

                    if(Password != null) {
                        Intent intent = new Intent(Signup_1Page.this, Signup_2Page.class);
                        intent.putExtra(EMAIL, Email);
                        intent.putExtra(PASSWORD, Password);
                        intent.putExtra(NAME, Name);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Security system failed", Toast.LENGTH_LONG).show();
                    }
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

    private boolean checkEmail () {

        if(Email.length() > 19) {
            String address = Email.substring(Email.length()-18,Email.length());
            //Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();
            if(address.equals("@email.arizona.edu"))
                return true;
        }
        return false;
    }

}
