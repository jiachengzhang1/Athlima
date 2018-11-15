package com.csc436.jz.sportsgroupup.Tasks;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetComingEventsTask extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ScrollView scrollView;

    public GetComingEventsTask (Context context, ScrollView scrollView) {

        this.context = context;
        this.scrollView = scrollView;
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
        ArrayList<Map<String, String>> eventList = new ArrayList<>();

        try {
            myJSONArray = new JSONArray(result);
            for(int i = 0; i<myJSONArray.length(); i++) {
               // JSONObject object = new JSONObject(myJSONArray.get(i).toString());
                JSONObject object = new JSONObject(myJSONArray.get(i).toString());

                // a map for each event
                Map<String, String> event = new HashMap<>();
                event.put("id", object.getString("id"));
                event.put("title", object.getString("name"));
                event.put("date", object.getString("date"));
                event.put("time", object.getString("time"));
                event.put("location", object.getString("location"));
                event.put("skill", object.getString("skill"));
                event.put("description", object.getString("description"));
                event.put("teamSize", object.getString("teamSize"));

                // add event map into eventList
                eventList.add(event);

            }

            // eventList has a list of event, the info of each event are carried by a map

            Toast.makeText(context, eventList.toString(), Toast.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(result == null) {
            Toast.makeText(context, "Server error for loading events", Toast.LENGTH_LONG).show();
        }
    }
}
