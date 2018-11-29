package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signInPage_intent = getIntent();

        currentUser = (CurrentUser) signInPage_intent.getSerializableExtra(MainActivity.USERINFO);


        LinearLayout scrollView = findViewById(R.id.scrollViewMain);
        LinearLayout layoutAttendee = findViewById(R.id.attendee_Layout);
        LayoutInflater inflater = (LayoutInflater) getApplication().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.custom_layout,null);
        popupWindow = new PopupWindow(customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        if(Build.VERSION.SDK_INT>=21){
            popupWindow.setElevation(5.0f);
        }

        // set action for close button of popup windown
        Button closeButton = customView.findViewById(R.id.ib_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the popup window
                popupWindow.dismiss();
            }
        });

        // parent view of the popup window
        RelativeLayout mRelativeLayout;
        mRelativeLayout = findViewById(R.id.rl);

        // access to the internet in order to get the events information
        String url = URL.Address.url + ":3000/get/searchAllEvent";
        new GetEventTask(eventList, getApplicationContext(), currentUser, scrollView, popupWindow, layoutAttendee, mRelativeLayout).execute(url);


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
           // startIntent.addFlags(1);
            startActivity(startIntent);
        } else if (id == R.id.nav_myEvents) {
            Intent startIntent = new Intent(getApplicationContext(), MyEventsPage.class);
            startIntent.putExtra(USER, currentUser);
            startActivity(startIntent);
        } else if (id == R.id.nav_createEvent) {
            createEvent();
        } else if (id == R.id.nav_setting) {
            Intent startIntent = new Intent(getApplicationContext(), Settings.class);
            //startIntent.putExtra()
            startActivity(startIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createEvent () {
        Intent startIntent = new Intent(getApplicationContext(), CreateEventPage.class);
        startIntent.putExtra(USER, currentUser);
        startActivity(startIntent);
    }

}
