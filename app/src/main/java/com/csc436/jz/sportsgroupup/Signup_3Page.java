package com.csc436.jz.sportsgroupup;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
                EditText personalStatement_view = (EditText) findViewById(R.id.personalStatement);
                personalStatement = personalStatement_view.getText().toString();
                String url = String.format("http://10.0.2.2:3000/get/createUser=%s,pwd=%s,readName=%s,ps=%s,preferSport=%s,schoolYear=%s",
                        email, password, name, personalStatement, sports, schoolYear);
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

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            InputStreamReader inputStreamReader = null;

            try {
                URL url = new URL(urls[0]);

                connection = (HttpURLConnection) url.openConnection();

                inputStreamReader = new InputStreamReader(connection.getInputStream());

                return "Sign up Successfully!";

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
            return "Failed";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

         //   test_view = (TextView) findViewById(R.id.test_view_page3);
         //   test_view.setText(result);

            // show sign up results
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            // if user sign up successfully, go to login page
            if (!result.equals("Failed")) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        }
    }

}
