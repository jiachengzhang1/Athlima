package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.csc436.jz.sportsgroupup.Tasks.GetComingEventsTask;
import com.csc436.jz.sportsgroupup.Tools.CurrentUser;
import com.csc436.jz.sportsgroupup.Tools.URL;

public class MyEventsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events_page);

        TextView textView = new TextView(getApplicationContext());
        ScrollView scrollView = findViewById(R.id.scrollView_goingEvents);

        Intent signin_intent = getIntent();
        CurrentUser currentUser = (CurrentUser) signin_intent.getSerializableExtra(MainPage.USER);

        String url = URL.Address.url + ":3000/get/";
        url = String.format(url + "/userEvent=%s", currentUser.getCurrentUserEmail());

        /* connecting to server by calling GetComingEventTask
        *  you can pass variables from this class to the task by add things into constructor
        *  and in GetComingEventTask, context = getApplicationContext(); scrollview is useless now
        * */
        new GetComingEventsTask(getApplicationContext(),scrollView).execute(url);

    }
}
