package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

        Button back =  findViewById(R.id.myEvents_back);
        LinearLayout scrollView = findViewById(R.id.myEvents_linearLayout);

        Intent signin_intent = getIntent();
        CurrentUser currentUser = (CurrentUser) signin_intent.getSerializableExtra(MainPage.USER);

        String url = URL.Address.url + ":3000/get/";
        url = String.format(url + "/userEvent=%s", currentUser.getCurrentUserEmail());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /* connecting to server by calling GetComingEventTask
        *  you can pass variables from this class to the task by add things into constructor
        *  and in GetComingEventTask, context = getApplicationContext(); scrollview is useless now
        * */
        new GetComingEventsTask(getApplicationContext(),scrollView).execute(url);

    }
}
