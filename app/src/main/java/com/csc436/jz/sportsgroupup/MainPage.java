package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.csc436.jz.sportsgroupup.Tasks.GetEventTask;
import com.csc436.jz.sportsgroupup.Tools.CurrentUser;
import com.csc436.jz.sportsgroupup.Tools.URL;

import java.util.ArrayList;
import java.util.Map;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String USER = "com.csc436.jz.sportsgroupup.USER";

    private Intent signInPage_intent;
    private ArrayList<Map<String, String>> eventList;
    private CurrentUser currentUser;
    private PopupWindow popupWindow;
    private LinearLayout layoutAttendee = findViewById(R.id.attendee_Layout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signInPage_intent = getIntent();

        ArrayList<String> userInfo = signInPage_intent.getStringArrayListExtra(MainActivity.USERINFO);

        currentUser = new CurrentUser(Integer.parseInt(userInfo.get(0)),
                userInfo.get(1), // email
                Integer.parseInt(userInfo.get(3)), // school year
                userInfo.get(4), // sport
                userInfo.get(5), // statement
                userInfo.get(6)); // name

        LinearLayout scrollView = findViewById(R.id.scrollViewMain);

        // access to the internet in order to get the events information
        String url = URL.Address.url + ":3000/get/searchAllEvent";
        new GetEventTask(eventList, getApplicationContext(), currentUser, scrollView, popupWindow, layoutAttendee).execute(url);


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
            startIntent.putExtra(USER, currentUser);
            startActivity(startIntent);
        } else if (id == R.id.nav_myEvents) {
            Intent startIntent = new Intent(getApplicationContext(), MyEventsPage.class);
            startIntent.putExtra(USER, currentUser);
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

}
