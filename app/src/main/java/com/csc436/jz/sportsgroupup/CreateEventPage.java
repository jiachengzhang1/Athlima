package com.csc436.jz.sportsgroupup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.csc436.jz.sportsgroupup.Tasks.CreateEventTask;
import com.csc436.jz.sportsgroupup.Tools.URL;

import java.util.Calendar;

public class CreateEventPage extends AppCompatActivity {

    private TextView displayDate;
    private EditText title, location, description;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_page);

        displayDate = findViewById(R.id.createEvent_text_date);
        Button createEvent = findViewById(R.id.button_createEvent);
        title = findViewById(R.id.createEvent_eventTitle);
        location = findViewById(R.id.createEvent_location);
        description = findViewById(R.id.createEvent_description);


        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateEventPage.this,android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month ++ ;
                String date = month + "-" + dayOfMonth + "-" + year;
                displayDate.setText(date);
            }
        };

        createEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Snackbar createEventNotify = Snackbar.make(findViewById(R.id.eventPage), "Event Created", Snackbar.LENGTH_SHORT);
                createEventNotify.show();

                // getting user inputs
                String title_str = title.getText().toString();
                String date_str = displayDate.getText().toString();
                String time_str = "1234";
                String location_str = location.getText().toString();
                String description_str = description.getText().toString();
                char skillLevel = 'a';
                int size = 3;

                @SuppressLint("DefaultLocale") String url = String.format("%s:3000/get/createEvent=%s,date=%s,time=%s,location=%s,skill=%s,description=%s,teamSize=%s",
                        URL.Address.url, title_str, date_str, time_str, location_str, skillLevel, description_str, size);
                new CreateEventTask().execute(url);

                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(intent);
            }
        });

    }

}
