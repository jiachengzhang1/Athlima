package com.csc436.jz.sportsgroupup.Tasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressLint("StaticFieldLeak")
public class CreateEventTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... urls) {

        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;

        java.net.URL url = null;
        try {
            url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            inputStreamReader = new InputStreamReader(connection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
}
