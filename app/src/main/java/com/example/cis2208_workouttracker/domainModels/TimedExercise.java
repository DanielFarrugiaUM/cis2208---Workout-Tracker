package com.example.cis2208_workouttracker.domainModels;

import java.sql.Time;

public class TimedExercise extends Exercise{

    public Time time;

    public TimedExercise(String name, int noOfSets, double weight, Time time) {
        super(name, noOfSets, weight);
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
