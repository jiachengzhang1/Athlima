package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.loginButton);
        Button signup = (Button) findViewById(R.id.signupButton);

        // click login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText username = findViewById(R.id.usernameText);
                EditText password = findViewById(R.id.passwordText);

                Intent startIntent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(startIntent);
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

        // test_button and test_view are testing internet connection
        Button test_button = (Button) findViewById(R.id.test_button);
        test_view = (TextView) findViewById(R.id.test_view);

        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SigninTask().execute("http://10.0.2.2:3000/get/searchallevent");
            }
        });
    }

    /*
     * Note:
     * 1. when the server is running on the server, network_security_config needs to be delete and
     * modify manifest for security reasons
     *
     * 2. the URL is being used for android emulator only, it may be changed for a real android phone
     *
     */
    private TextView test_view;
    public class SigninTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();

                InputStream stream = connection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                return e.toString();
            } catch (IOException e) {
                return e.toString();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //   return "Connection Error";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            test_view.setText(result);
        }
    }
}
