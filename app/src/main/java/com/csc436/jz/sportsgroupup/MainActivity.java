package com.csc436.jz.sportsgroupup;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public final static String USERINFO = "com.csc436.jz.sportsgroupup.USERINFO";
    public final static String EVENTLIST = "com.csc436.jz.sportsgroupup.EVENTLIST";

    private EditText username, password;
    private Intent startIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.loginButton);
        Button signup = findViewById(R.id.signupButton);

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
                        com.csc436.jz.sportsgroupup.URL.Address.url, username_str,password_str);
                new SigninTask().execute(url);

            }
        });

        // click signup button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ShowPopUp.class);
                startActivity(startIntent);
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
    @SuppressLint("StaticFieldLeak")
    public class SigninTask extends AsyncTask<String, String, String> {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(strings[0]);

                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                StringBuilder content;

                // read input stream
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {

                    String line;
                    content = new StringBuilder();

                    while ((line = in.readLine()) != null) {
                        content.append(line);
                        content.append(System.lineSeparator());
                    }
                }
                return content.toString();

            } catch (MalformedURLException e) {
                return e.toString();
            } catch (IOException e) {
                return e.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "-1";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            boolean loadUser = verifyAndGetUserInfo(result);
            if (loadUser) {
                Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                startActivity(startIntent);
            } else {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            }
        }

        private boolean verifyAndGetUserInfo (String result) {
            boolean status = false;
            if (result != null) {
                if (result == "") {}
                else {
                    // decoding json object
                    JSONArray myJSONArray = null;
                    try {
                        myJSONArray = new JSONArray(result);
                        JSONObject object = new JSONObject(myJSONArray.get(0).toString());

                        ArrayList<String> userInfo = new ArrayList<String>();

                        userInfo.add(object.getString("id"));
                        userInfo.add(object.getString("emailAddress"));
                        userInfo.add(object.getString("password"));
                        userInfo.add(object.getString("schoolYear"));
                        userInfo.add(object.getString("prefer_sport"));
                        userInfo.add(object.getString("ps"));
                        userInfo.add(object.getString("readName"));

                        // share data to next activity and jump to the next page
                        startIntent.putExtra(USERINFO, userInfo);

                        status = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else { }
            return status;
        }

    }

}
