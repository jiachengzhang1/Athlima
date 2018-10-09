package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Signup_2Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent page1_intent = getIntent();
      //  String email = intent.getStringExtra(Signup_1Page.EMAIL);
      //  String password = intent.getStringExtra(Signup_1Page.PASSWORD);
      //  String name = intent.getStringExtra(Signup_1Page.NAME);

     //   System.out.println(email + " " + password+" "+name);

        setContentView(R.layout.activity_signup_2_page);

        Button signUp2Next = findViewById(R.id.signup_next);
        Button signUp2Back = findViewById(R.id.signup_back);
        //signUp2Back.setText(page1_intent.getStringExtra(Signup_1Page.EMAIL));

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.signup_radiogroup);


        signUp2Next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){


                int selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID != -1) {
                    String text = ((RadioButton) findViewById(selectedID)).getText().toString();
                } else {

                }

                Intent intent = new Intent(Signup_2Page.this, Signup_3Page.class);
                intent.putExtras(page1_intent.getExtras());
                startActivity(intent);
            }
        } );

        signUp2Back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        } );
    }
}
