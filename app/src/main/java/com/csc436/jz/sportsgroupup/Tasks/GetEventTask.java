package com.csc436.jz.sportsgroupup.Tasks;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.csc436.jz.sportsgroupup.Tools.CurrentUser;
import com.csc436.jz.sportsgroupup.Tools.URL;

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

public class GetEventTask extends AsyncTask<String, String, String> {

    private ArrayList<Map<String, String>> eventList;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private CurrentUser currentUser;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout scrollView;
    private PopupWindow popupWindow;
    private LinearLayout layoutAttendee;

    public GetEventTask(ArrayList<Map<String, String>> eventList,
                        Context context,
                        CurrentUser currentUser,
                        LinearLayout scrollView,
                        PopupWindow popupWindow,
                        LinearLayout layoutAttendee) {

        this.context = context;
        this.eventList = eventList;
        this.currentUser = currentUser;
        this.scrollView = scrollView;
        this.popupWindow = popupWindow;
        this.layoutAttendee = layoutAttendee;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection connection = null;
        try {
            java.net.URL url = new java.net.URL(urls[0]);

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
            Toast.makeText(context, "Server error for loading events", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private boolean getAllEvents (String result) {
        JSONArray myJSONArray = null;

        try {
            myJSONArray = new JSONArray(result);
            eventList = new ArrayList<>();

            for(int i = 0; i<myJSONArray.length(); i++) {
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

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < eventList.size(); i++) {
                ConstraintLayout textAdd = new ConstraintLayout(context);

                Button but_join = new Button(context);
                but_join.setText("Join");
                but_join.setHeight(50);
                but_join.setWidth(300);
                but_join.setX(600);
                but_join.setY(100);
                String id_str = eventList.get(i).get("id");
                if(id_str != null) {
                    but_join.setId(Integer.parseInt(id_str));
                }

                Button but_coming = new Button(context);
                but_coming.setText("Attendees");
                but_coming.setHeight(50);
                but_coming.setWidth(300);
                but_coming.setX(600);
                but_coming.setY(0);
                if(id_str != null) {
                    but_coming.setId(Integer.parseInt(id_str));
                }


                but_join.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onClick(View v) {

                        String url = URL.Address.url + ":3000/get/";
                        url = String.format(url + "joinEvent=%d,user=%s", v.getId(), currentUser.getCurrentUserEmail());
                        new JoinTask(context).execute(url);

                    }
                });


                but_coming.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onClick(View v) {
                        String url = URL.Address.url + ":3000/get/";
                        url = String.format(url + "attendee=%d", v.getId());
                        new ShowAttendeesTask(context,  popupWindow).execute(url);
                    }
                });

                TextView title = new TextView(context);
                title.setText("Title: " + eventList.get(i).get("title") +
                        "\nDate: " + eventList.get(i).get("date") +
                        "\nLocation: " + eventList.get(i).get("Location") +
                        "\nDescription: " + eventList.get(i).get("Description") +"\n\n");

                title.setX(20);

                Map<String, String> temp = eventList.get(i);
                int eventID = -1;

                if (temp != null && temp.get("id") != null) {
                    eventID = Integer.parseInt(temp.get("id"));
                    textAdd.addView(title, param);
                    textAdd.addView(but_join);
                    textAdd.addView(but_coming);
                    final int finalEventID = eventID;


                    scrollView.addView(textAdd, param);

                }

            }

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}