package com.csc436.jz.sportsgroupup.Tasks;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class ShowAttendeesTask extends AsyncTask<String, String, String> {

    private Context context;
    String a;

    public ShowAttendeesTask (Context context) {
        this.context = context;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        try {
            java.net.URL url = new java.net.URL(strings[0]);
            a=strings[0];
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
        boolean loadAttendees = getAllAttendees(result);
        if(loadAttendees == false) {
            Toast.makeText(context, "Server Failed", Toast.LENGTH_LONG).show();
        }
    }

    private boolean getAllAttendees (String result) {

        JSONArray myJSONArray = null;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        try {
            myJSONArray = new JSONArray(result);
            ArrayList<String> attendeeList = new ArrayList();
            String test = "";

            // decoding json object of attendees list
            for(int i = 0; i<myJSONArray.length(); i++) {
                JSONObject object = new JSONObject(myJSONArray.get(i).toString());
                Button but_attendee = new Button(context);
                attendeeList.add(object.getString("readName"));
            }
            Toast.makeText(context, attendeeList.toString(), Toast.LENGTH_LONG).show();

            /*
            * To do here
            *
            * make a popup window for attendees or whatever you think that looks good
            *
            * */


            return true;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return false;
    }
}
