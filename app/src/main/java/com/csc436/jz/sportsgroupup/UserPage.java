package com.csc436.jz.sportsgroupup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.csc436.jz.sportsgroupup.Tools.CurrentUser;

import java.io.InputStream;
import java.util.ArrayList;

public class UserPage extends AppCompatActivity {

    private CurrentUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        Intent userInfo_intent = getIntent();

        user = (CurrentUser) userInfo_intent.getSerializableExtra(MainPage.USER);

        // userInfo list, id, email, password, school year, sport, statement
        ArrayList<String> userInfo = userInfo_intent.getStringArrayListExtra(MainActivity.USERINFO);

        ImageView userImage = findViewById(R.id.userPage_userImage);
        String pathToFile = "http://149.28.88.218:8099/downloads/EFFW3516.jpg";
        DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(userImage);
        downloadTask.execute(pathToFile);

        // setting text for user information
        TextView email = findViewById(R.id.userPage_email);
        TextView name = findViewById(R.id.userPage_name);
        TextView statement = findViewById(R.id.userPage_statement);
        TextView schoolYear = findViewById(R.id.userPage_schoolYear);

        name.setText(user.getName());
        email.setText(user.getCurrentUserEmail());
        statement.setText(user.getStatement());
        schoolYear.setText(user.getSchoolYear());

        Button back, logout;

        back = findViewById(R.id.userPage_back);
        logout = findViewById(R.id.user_logout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = null;
                finish();
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

    }


    public class DownloadImageWithURLTask extends AsyncTask <String, Void, Bitmap> {

        ImageView bitmapImage;
        public DownloadImageWithURLTask(ImageView bmImage) {
            this.bitmapImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String pathToFile = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bitmapImage.setImageBitmap(result);
        }


    }
}
