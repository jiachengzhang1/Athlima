package com.csc436.jz.sportsgroupup.Tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class JoinTask extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public JoinTask(Context context) {
        this.context = context;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        java.net.URL url = null;
        try {
            url = new java.net.URL(urls[0]);
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result == null) {
            Toast.makeText(context, "Server error for joining events", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "You Joined!", Toast.LENGTH_LONG).show();
        }
    }
}