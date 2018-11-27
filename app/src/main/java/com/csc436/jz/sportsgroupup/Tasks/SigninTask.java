package com.csc436.jz.sportsgroupup.Tasks;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.csc436.jz.sportsgroupup.MainActivity;
import com.csc436.jz.sportsgroupup.Tools.CurrentUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/*
 * Note:
 * 1. when the server is running on the server, network_security_config needs to be delete and
 * modify manifest for security reasons
 *
 * 2. the URL is being used for android emulator only, it may be changed for a real android phone
 *
 */
@SuppressLint("StaticFieldLeak")
public class SigninTask extends AsyncTask<String, String, String> {

    private Context context;
    private Intent intent;
    private MainActivity mainActivity;

    public SigninTask(Context context, Intent intent, MainActivity mainActivity) {
        this.context = context;
        this.intent = intent;
        this.mainActivity = mainActivity;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;

        try {
            java.net.URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            StringBuilder content;

            // read input stream
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            return content.toString();

        } catch (MalformedURLException e) {
            return e.toString();
        } catch (IOException e) {
            return e.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        boolean loadUser = verifyAndGetUserInfo(result);

        if (loadUser) {
            mainActivity.startActivity(intent);
        }

    }

    private boolean verifyAndGetUserInfo(String result) {
        if (result != null) {

            // decoding json object
            JSONArray myJSONArray = null;
            try {
                myJSONArray = new JSONArray(result);
                if(myJSONArray.length() < 1) {
                    Toast.makeText(context, "Account doesn't match the record.", Toast.LENGTH_LONG).show();
                    return false;
                }
                JSONObject object = new JSONObject(myJSONArray.get(0).toString());

                CurrentUser currentUser = new CurrentUser(Integer.parseInt(object.getString("id")),
                        object.getString("emailAddress"), // email
                        Integer.parseInt(object.getString("schoolYear")), // school year
                        object.getString("prefer_sport"), // sport
                        object.getString("ps"), // statement
                        object.getString("readName")); // name

                // share data to next activity and jump to the next page
                intent.putExtra(MainActivity.USERINFO, currentUser);
                Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show();
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(context, "Account doesn't match the record.", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(context, "Server Error.", Toast.LENGTH_LONG).show();
        return false;
    }

}

