package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class Signup_2Page extends AppCompatActivity {
    private ArrayList<String> sportList = new ArrayList<String>();
    private CheckBox[] checkBoxes = new CheckBox[9];
    CheckBox football,soccer,baseball,basketball,golf,volleyball,frisbee,tennis,hockey;
    public final static String SCHOOLYEAR = "com.csc436.jz.sportsgroupup.SCHOOL";
    public final static String SPORTLIST = "com.csc436.jz.sportsgroupup.SPORTLIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent page1_intent = getIntent();
        String email = page1_intent.getStringExtra(Signup_1Page.EMAIL);
        String password = page1_intent.getStringExtra(Signup_1Page.PASSWORD);
        String name = page1_intent.getStringExtra(Signup_1Page.NAME);

        setContentView(R.layout.activity_signup_2_page);


        // creating checkboxes
        football = (CheckBox) findViewById(R.id.signup_football);
        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    sportList.add(football.getText().toString());
                }
            }
        });
        soccer = (CheckBox) findViewById(R.id.signup_soccer);
        soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    sportList.add(soccer.getText().toString());
                }
            }
        });
        baseball = (CheckBox) findViewById(R.id.signup_baseball);
        baseball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    sportList.add(baseball.getText().toString());
                }
            }
        });
        basketball = (CheckBox) findViewById(R.id.signup_basketball);
        basketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    sportList.add(basketball.getText().toString());
                }
            }
        });
        volleyball = (CheckBox) findViewById(R.id.signup_volleyball);
        volleyball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    sportList.add(volleyball.getText().toString());
                }
            }
        });
        frisbee = (CheckBox) findViewById(R.id.signup_frisbee);
        frisbee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    sportList.add(frisbee.getText().toString());
                }
            }
        });
        tennis = (CheckBox) findViewById(R.id.signup_tennis);
        tennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    sportList.add(tennis.getText().toString());
                }
            }
        });
        hockey = (CheckBox) findViewById(R.id.signup_hockey);
        hockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    sportList.add(hockey.getText().toString());
                }
            }
        });
        golf = (CheckBox) findViewById(R.id.signup_golf);
        golf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    sportList.add(golf.getText().toString());
                }
            }
        });




        Button signUp2Next = findViewById(R.id.signup_next);
        Button signUp2Back = findViewById(R.id.signup_back);
        //signUp2Back.setText(page1_intent.getStringExtra(Signup_1Page.EMAIL));

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.signup_radiogroup);

        signUp2Next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){


                int selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == -1) { // no radio button is chosen

                } else { // one radio button is selected
                    Intent intent = new Intent(Signup_2Page.this, Signup_3Page.class);

                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedID);
                    String schoolYear = selectedRadioButton.getText().toString();

                    intent.putExtras(page1_intent.getExtras());
                    intent.putExtra(SCHOOLYEAR, schoolYear);
                    intent.putExtra(SPORTLIST, sportList);
                    startActivity(intent);
                }

            }
        } );

        signUp2Back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent startIntent = new Intent(getApplicationContext(), Signup_1Page.class);
                startActivity(startIntent);
            }
        } );
    }

}
