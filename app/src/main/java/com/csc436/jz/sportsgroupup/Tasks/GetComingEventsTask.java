package com.csc436.jz.sportsgroupup.Tasks;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class GetComingEventsTask extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public GetComingEventsTask (Context context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        try {
            java.net.URL url = new java.net.URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            StringBuilder content;

            // read input stream
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

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
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        JSONArray myJSONArray = null;
        try {
            myJSONArray = new JSONArray(result);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(result == null) {
            Toast.makeText(context, "Server error for loading events", Toast.LENGTH_LONG).show();
        }
    }
}
