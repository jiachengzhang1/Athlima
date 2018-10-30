package com.csc436.jz.sportsgroupup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;

public class CreateEventPage extends AppCompatActivity {

    private TextView displayDate;
    private Button createEvent;
    private EditText title, location, description;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_page);

        displayDate = findViewById(R.id.createEvent_text_date);
        createEvent = findViewById(R.id.button_createEvent);
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

                String title_str = title.getText().toString();
                String date_str = displayDate.getText().toString();
                String time_str = "1234";
                String location_str = location.getText().toString();
                String description_str = description.getText().toString();
                char skillLevel = 'a';
                int size = 3;

                String url = String.format("http://10.0.2.2:3000/get/createEvent=%s,date=%s,time=%s,location=%s,skill=%s,description=%s,teamSize=%d",
                        title_str, date_str, time_str, location_str, skillLevel, description_str, size);
                new CreateEventTask().execute(url);



                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(intent);
            }
        });

    }


    public class CreateEventTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {

            HttpURLConnection connection = null;
            InputStreamReader inputStreamReader = null;


            URL url = null;
            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                inputStreamReader = new InputStreamReader(connection.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connection.disconnect();
                }
            }


            return null;
        }
    }

}
