package com.csc436.jz.sportsgroupup;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button red = findViewById(R.id.redColor);
        Button blue = findViewById(R.id.blueColor);
        Button white = findViewById(R.id.defaultColor);
        Button back = findViewById(R.id.setting_back);
        final ConstraintLayout settingsLayout = findViewById(R.id.settingsLayout);


        red.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                settingsLayout.setBackgroundColor(Color.RED);
            }
        });

        white.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                settingsLayout.setBackgroundColor(Color.WHITE);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                settingsLayout.setBackgroundColor(Color.BLUE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
