package com.csc436.jz.sportsgroupup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ShowPopUp extends AppCompatActivity {

    boolean click = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pop_up);

        final PopupWindow popUp = new PopupWindow(this);
        final LinearLayout popLayout = new LinearLayout(this);
        TextView popText = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout mainLayout = new LinearLayout(this);
        Button but = new Button(this);
        but.setText("Click Me");
        but.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (click) {
                    popUp.showAtLocation(popLayout, Gravity.BOTTOM, 10, 10);
                    popUp.update(50, 50, 300, 80);
                    click = false;
                } else {
                    popUp.dismiss();
                    click = true;
                }
            }

        });
        popLayout.setOrientation(LinearLayout.VERTICAL);
        popLayout.addView(popText, params);
        popUp.setContentView(popLayout);
        mainLayout.addView(but, params);
        setContentView(mainLayout);

    }
}


