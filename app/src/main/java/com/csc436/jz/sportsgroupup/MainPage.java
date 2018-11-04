package com.csc436.jz.sportsgroupup;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent signInPage_intent;
    private ArrayList<Map<String, String>> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signInPage_intent = getIntent();

        // access to the internet in order to get the events information
        String url = com.csc436.jz.sportsgroupup.URL.Address.url + ":3000/get/searchAllEvent";
        new GetEventTask().execute(url);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            Intent startIntent = new Intent(getApplicationContext(), UserPage.class);
            startIntent.putExtras(signInPage_intent.getExtras());
            startActivity(startIntent);
        } else if (id == R.id.nav_myEvents) {
            Intent startIntent = new Intent(getApplicationContext(), MyEventsPage.class);
            startActivity(startIntent);
        } else if (id == R.id.nav_createEvent) {
            createEvent();
        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createEvent () {
        Intent startIntent = new Intent(getApplicationContext(), CreateEventPage.class);
        startActivity(startIntent);
    }

    public class GetEventTask extends AsyncTask<String, String, String> {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;

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
                try {
                    if (bufferedReader != null) {
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
            boolean loadEvents = getAllEvents(result);
            if( !loadEvents ) {
                Toast.makeText(getApplicationContext(), "Server error for loading events", Toast.LENGTH_LONG).show();
            }
        }

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
                LinearLayout test1 = findViewById(R.id.scrollViewMain);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                for (int i = 0; i < eventList.size(); i++) {
                    ConstraintLayout textAdd = new ConstraintLayout(getApplicationContext());
                    Button but = new Button(getApplicationContext());
                    but.setText("Join");
                    but.setHeight(50);
                    but.setWidth(200);
                    but.setX(600);
                    but.setY(100);
                    TextView title = new TextView(getApplicationContext());
                    title.setText("Title: " + eventList.get(i).get("title") +
                            "\nDate: " + eventList.get(i).get("date") +
                            "\nLocation: " + eventList.get(i).get("Location") +
                            "\nDescription: " + eventList.get(i).get("Description") +"\n\n");

                    Map<String, String> temp = eventList.get(i);
                    int eventID = -1;

                    if (temp != null && temp.get("id") != null) {
                        eventID = Integer.parseInt(temp.get("id"));
                        textAdd.addView(title, param);
                        textAdd.addView(but);

                        final int finalEventID = eventID;


                        test1.addView(textAdd, param);

                    }

                }

                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

    }

}
