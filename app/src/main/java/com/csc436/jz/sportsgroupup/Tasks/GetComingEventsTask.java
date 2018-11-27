package com.csc436.jz.sportsgroupup.Tasks;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetComingEventsTask extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout scrollView;

    public GetComingEventsTask (Context context, LinearLayout scrollView) {

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
        super.onPostExecute(result);
        boolean loadEvents = getAllEvents(result);
        if( !loadEvents ) {
           //Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
        }
    }


    @SuppressLint("SetTextI18n")
    private boolean getAllEvents(String result) {
        JSONArray myJSONArray = null;
        ArrayList<Map<String, String>> eventList = new ArrayList<>();
        try {
            myJSONArray = new JSONArray(result);
            for (int i = myJSONArray.length()-1; i>=0; i--) {
                // JSONObject object = new JSONObject(myJSONArray.get(i).toString());
                JSONObject object = new JSONObject(myJSONArray.get(i).toString());

                if(Integer.parseInt(object.getString("id")) == -1) {
                    Toast.makeText(context, "Your Events are empty", Toast.LENGTH_LONG).show();
                    return false;
                }

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
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < eventList.size() - 1; i++) {
                ConstraintLayout textAdd = new ConstraintLayout(context);
                TextView title = new TextView(context);
                title.setText("Title: " + eventList.get(i).get("title") +
                        "\nDate: " + eventList.get(i).get("date") +
                        "\nLocation: " + eventList.get(i).get("location") +
                        "\nDescription: " + eventList.get(i).get("description") + "\n\n");

                title.setX(20);

                Map<String, String> temp = eventList.get(i);

                if (temp != null && temp.get("id") != null) {
                    textAdd.addView(title, param);
                    scrollView.addView(textAdd, param);

                }
            }

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Server Error.", Toast.LENGTH_LONG).show();
        return false;
    }
}
