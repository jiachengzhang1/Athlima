package com.csc436.jz.sportsgroupup.Tools;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentUser implements Serializable {

    private String currentUserEmail, sport, statement, name, schoolYear;
    private ArrayList myEvents;
    private int currentUserId;


    public CurrentUser(int id, String user,
                       int schoolYear,
                       String sport,
                       String statement,
                       String name) {

        this.currentUserEmail = user;
        this.currentUserId = id;
        this.sport = sport;
        this.statement = statement;
        this.name = name;

        setSchoolYear(schoolYear);
    }

    public String getCurrentUserEmail() {
        return this.currentUserEmail;
    }

    public int getCurrentUserId() {
        return this.currentUserId;
    }

    public String getSport () {
        return this.sport;
    }

    public String getStatement () {
        return this.statement;
    }

    public String getName () {
        return this.name;
    }

    public String getSchoolYear () {
        return this.schoolYear;
    }



    private void setSchoolYear(int userSchoolYear) {
        if(userSchoolYear == 1) {
            this.schoolYear = "Freshman";
        } else if (userSchoolYear == 2) {
            this.schoolYear = "Sophomore";
        } else if (userSchoolYear == 3) {
            this.schoolYear = "Junior";
        } else if (userSchoolYear == 4) {
            this.schoolYear = "Senior";
        } else if (userSchoolYear == 5) {
            this.schoolYear = "Grad School";
        } else {
            this.schoolYear = "Phd";
        }
    }

}
