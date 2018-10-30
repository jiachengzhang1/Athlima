package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class UserPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        Intent userInfo_intent = getIntent();

        // userInfo list, id, email, password, school year, sport, statement
        ArrayList<String> userInfo = userInfo_intent.getStringArrayListExtra(MainActivity.USERINFO);

        String userEmail = userInfo.get(1);
        String userPassword = userInfo.get(2);
        int userSchoolYear = Integer.parseInt(userInfo.get(3));
        String userSport = userInfo.get(4);
        String userStatement = userInfo.get(5);
        String userName = userInfo.get(6);


        TextView email = findViewById(R.id.userPage_email);
        TextView name = findViewById(R.id.userPage_name);
        TextView statement = findViewById(R.id.userPage_statement);
        TextView schoolYear = findViewById(R.id.userPage_schoolYear);

        name.setText(userName);
        email.setText(userEmail);
        if(userSchoolYear == 1) {
            schoolYear.setText("Freshman");
        } else if (userSchoolYear == 2) {
            schoolYear.setText("Sophmore");
        } else if (userSchoolYear == 3) {
            schoolYear.setText("Junior");
        } else if (userSchoolYear == 4) {
            schoolYear.setText("Senior");
        } else if (userSchoolYear == 5) {
            schoolYear.setText("Grad School");
        } else {
            schoolYear.setText("Phd");
        }
        statement.setText(userStatement);


    }
}
