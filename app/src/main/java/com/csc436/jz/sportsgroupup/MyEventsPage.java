package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;

import com.csc436.jz.sportsgroupup.Tasks.GetComingEventsTask;
import com.csc436.jz.sportsgroupup.Tools.CurrentUser;
import com.csc436.jz.sportsgroupup.Tools.URL;

public class MyEventsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events_page);

        Intent signin_intent = getIntent();
        CurrentUser currentUser = (CurrentUser) signin_intent.getSerializableExtra(MainPage.USER);

        ScrollView scrollView = findViewById(R.id.scrollView_goingEvents);
        String url = URL.Address.url + ":3000/get/";
        url = String.format(url + "searchUser=%s", currentUser.getCurrentUserEmail());
        new GetComingEventsTask(getApplicationContext()).execute(url);

    }
}
