package com.example.cis2208_workouttracker.domainModels;

import android.os.Build;

import java.sql.Date;
import java.time.LocalDate;

public class HistoryItem {
    //Cannot use the new java.time.LocalDate due to SDK21
    //it needs to be 26 or higher
    public Date date;
    public Exercise exercise;

    public HistoryItem(Exercise exercise){
        this.date = new Date(System.currentTimeMillis());
        this.exercise = exercise;
    }
}
