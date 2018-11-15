package com.csc436.jz.sportsgroupup.Tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.csc436.jz.sportsgroupup.MainActivity;
import com.csc436.jz.sportsgroupup.Signup_3Page;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressLint("StaticFieldLeak")
public class SignupTask extends AsyncTask<String, String, String> {

    private Context context;
    private String email;
    private Signup_3Page signup_3Page;

    public SignupTask (Context context, String email, Signup_3Page signup_3Page) {
        this.context = context;
        this.email = email;
        this.signup_3Page = signup_3Page;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;

        try {
            java.net.URL url = new URL(urls[0]);

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
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            try {

                JSONObject object = new JSONObject(result);

                boolean status = object.getBoolean("boolean");
                if(status){
                    Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
                    Intent startIntent = new Intent(context, MainActivity.class);
                    signup_3Page.startActivity(startIntent);
                } else {
                    Toast.makeText(context, email + " has been registered!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}