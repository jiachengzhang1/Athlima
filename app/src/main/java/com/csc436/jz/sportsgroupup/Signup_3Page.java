package com.csc436.jz.sportsgroupup;


import android.annotation.SuppressLint;
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
import java.util.ArrayList;

public class Signup_3Page extends AppCompatActivity {

   // private TextView test_view;
    private String email, password, name,  sports, personalStatement, schoolYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_3_page);

        // getting all data from previous pages
        final Intent page2_intent = getIntent();
        email = page2_intent.getStringExtra(Signup_1Page.EMAIL);
        password = page2_intent.getStringExtra(Signup_1Page.PASSWORD);
        name = page2_intent.getStringExtra(Signup_1Page.NAME);

        String temp = page2_intent.getStringExtra(Signup_2Page.SCHOOLYEAR);
        if(temp.equals("Freshman"))
            schoolYear = "1";
        else if (temp.equals("Sophmore"))
            schoolYear = "2";
        else if (temp.equals("Junior"))
            schoolYear = "3";
        else if (temp.equals("Senior"))
            schoolYear = "4";
        else if (temp.equals("Grad School"))
            schoolYear = "5";
        else schoolYear = "6";

        ArrayList<String> sportList = page2_intent.getStringArrayListExtra(Signup_2Page.SPORTLIST);
        sports = "";
        for (int i = 0; i<sportList.size(); i++) {
            sports += sportList.get(i) + ";";
        }

        // two buttons for jump back or next
        Button signUpFinalFinish = findViewById(R.id.signUpFinalFinish);
        Button signUpFinalBack = findViewById(R.id.signUpFinalBack);

        signUpFinalFinish.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                EditText personalStatement_view = findViewById(R.id.personalStatement);
                personalStatement = personalStatement_view.getText().toString();
                if(personalStatement.equals("")){
                    personalStatement = " ";
                }
                String url = String.format("%s:3000/get/createUser=%s,pwd=%s,readName=%s,ps=%s,preferSport=%s,schoolYear=%s",
                        com.csc436.jz.sportsgroupup.URL.Address.url, email, password, name, personalStatement, sports, schoolYear);
                new SignupTask().execute(url);
            }
        } );

        signUpFinalBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent startIntent = new Intent(getApplicationContext(), Signup_2Page.class);
                startActivity(startIntent);
            }
        } );
    }

    @SuppressLint("StaticFieldLeak")
    public class SignupTask extends AsyncTask<String, String, String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            InputStreamReader inputStreamReader = null;

            try {
                URL url = new URL(urls[0]);

                connection = (HttpURLConnection) url.openConnection();

                inputStreamReader = new InputStreamReader(connection.getInputStream());


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
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
          // show sign up results
          //  Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            // if user sign up successfully, go to login page
            if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                try {

                    JSONObject object = new JSONObject(result);

                    boolean status = object.getBoolean("boolean");
                    if(status){
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(startIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), email + " has been registered!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
