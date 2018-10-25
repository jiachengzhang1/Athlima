package com.csc436.jz.sportsgroupup;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;

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

               // new SigninTask().execute("http://10.0.2.2:3000/get/searchAllEvent");
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
                new SigninTask().execute("http://10.0.2.2:3000/get/searchAllEvent");
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

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(urls[0]);

                // creating JSONObject
                username = findViewById(R.id.usernameText);
                password = findViewById(R.id.passwordText);
                String post_username = username.getText().toString();
                String post_password = password.getText().toString();


                connection = (HttpURLConnection) url.openConnection ();
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

                // decoding json object
                JSONArray myJSONArray = new JSONArray(content.toString());
                JSONObject object = new JSONObject(myJSONArray.get(0).toString());
                String id =  object.getString("id");
                String name =  object.getString("name");
                String date =  object.getString("date");
                String time =  object.getString("time");
                String location =  object.getString("location");
                String description =  object.getString("description");

                String test_result = "id is " + id + "\n"
                        + "name is " + name + "\n"
                        + "date is " + date + "\n"
                        + "time is " + time + "\n"
                        + "location is " + location+ "\n"
                        + "description is " + description;

                return test_result;

                /*
                if (!post_username.isEmpty() && !post_password.isEmpty()) {
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("email", post_username);
                    postDataParams.put("password", post_password);
                    Log.e("params", postDataParams.toString());


                    // build connection to url
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(15000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);


                    OutputStream outputStream = connection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(
                            new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(getPostDataString(postDataParams));
                    bufferedWriter.flush();;
                    bufferedWriter.close();
                    outputStream.close();

                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        bufferedReader = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));
                        StringBuffer stringBuffer = new StringBuffer("");
                        String line = "";
                        while((line = bufferedReader.readLine()) != null){
                            stringBuffer.append(line);
                            break;
                        }
                        bufferedReader.close();
                        return bufferedReader.toString();
                    } else {
                        return new String("false :"+responseCode);
                    }
                } */

            } catch (MalformedURLException e) {
                return e.toString();
            } catch (IOException e) {
                return e.toString();
            }  catch (Exception e) {
                e.printStackTrace();
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
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            test_view.setText(result);
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            if(result != null) {
                Intent startIntent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(startIntent);
            }
        }
    }
/*
    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();
        while(itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);
            if (first) first = false;
            else result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
    */

}
