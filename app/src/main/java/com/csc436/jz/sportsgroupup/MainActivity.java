package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csc436.jz.sportsgroupup.Tasks.SigninTask;
import com.csc436.jz.sportsgroupup.Tools.Password_Hash;
import com.csc436.jz.sportsgroupup.Tools.URL;

public class MainActivity extends AppCompatActivity {
    public final static String USERINFO = "com.csc436.jz.sportsgroupup.USERINFO";
    public final static String EVENTLIST = "com.csc436.jz.sportsgroupup.EVENTLIST";

    private EditText username, password;
    private Intent startIntent;
    private MainActivity mainActivity;


    //String a = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.loginButton);
        Button signup = findViewById(R.id.signupButton);
        mainActivity = this;

        // click login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = findViewById(R.id.usernameText);
                password = findViewById(R.id.passwordText);
                String username_str = username.getText().toString();
                String password_str = Password_Hash.SHA1(password.getText().toString());

                startIntent = new Intent(getApplicationContext(), MainPage.class);

                // verifying and getting user information
                String url = String.format("%s:3000/get/loginUserName=%s,pwd=%s",
                        URL.Address.url, username_str,password_str);
                new SigninTask(getApplicationContext(), startIntent, mainActivity).execute(url);

            }
        });

        // click signup button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Signup_1Page.class);
                startActivity(startIntent);
            }
        });
    }

}
