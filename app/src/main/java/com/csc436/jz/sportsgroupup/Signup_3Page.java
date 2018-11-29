package com.csc436.jz.sportsgroupup;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.csc436.jz.sportsgroupup.Tasks.SignupTask;
import com.csc436.jz.sportsgroupup.Tools.URL;

import java.util.ArrayList;

public class Signup_3Page extends AppCompatActivity {

    // private TextView test_view;
    private String email, password, name,  sports, personalStatement, schoolYear;
    private Signup_3Page signup_3Page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_3_page);

        // getting all data from previous pages
        final Intent page2_intent = getIntent();
        email = page2_intent.getStringExtra(Signup_1Page.EMAIL);
        password = page2_intent.getStringExtra(Signup_1Page.PASSWORD);
        name = page2_intent.getStringExtra(Signup_1Page.NAME);

        String temp = page2_intent.getStringExtra(Signup_2Page.SCHOOLYEAR);
        if(temp.equals("Freshman"))
            schoolYear = "1";
        else if (temp.equals("Sophomore"))
            schoolYear = "2";
        else if (temp.equals("Junior"))
            schoolYear = "3";
        else if (temp.equals("Senior"))
            schoolYear = "4";
        else if (temp.equals("Grad School"))
            schoolYear = "5";
        else schoolYear = "6";

        ArrayList<String> sportList = page2_intent.getStringArrayListExtra(Signup_2Page.SPORTLIST);
        sports = "";
        for (int i = 0; i<sportList.size(); i++) {
            sports += sportList.get(i) + ";";
        }

        // two buttons for jump back or next
        ImageButton signUpFinalFinish = findViewById(R.id.signUpFinalFinish);
        ImageButton signUpFinalBack = findViewById(R.id.signUpFinalBack);

        signup_3Page = this;

        signUpFinalFinish.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                EditText personalStatement_view = findViewById(R.id.personalStatement);
                personalStatement = personalStatement_view.getText().toString();
                if(personalStatement.equals("")){
                    personalStatement = " ";
                }
                String url = String.format("%s:3000/get/createUser=%s,pwd=%s,readName=%s,ps=%s,preferSport=%s,schoolYear=%s",
                        URL.Address.url, email, password, name, personalStatement, sports, schoolYear);
                new SignupTask(getApplicationContext(), email, signup_3Page).execute(url);
            }
        } );

        signUpFinalBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent startIntent = new Intent(getApplicationContext(), Signup_2Page.class);
                startActivity(startIntent);
            }
        } );
    }
}