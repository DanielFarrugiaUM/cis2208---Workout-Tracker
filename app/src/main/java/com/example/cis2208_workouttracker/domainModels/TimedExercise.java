package com.example.cis2208_workouttracker.domainModels;

import java.sql.Time;

public class TimedExercise extends Exercise{

    public Time time;

    public TimedExercise(long id, String name, int noOfSets, double weight, Time time, long workoutId) {
        super(id, name, noOfSets, weight, workoutId);
        this.time = time;
    }

    public TimedExercise(String name, int noOfSets, double weight, Time time, long workoutId) {
        super(name, noOfSets, weight, workoutId);
        this.time = time;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    //The are some utility methods
    public int getTotalSeconds(){
        return this.time.getSeconds() + this.time.getMinutes() * 60;
    }

    public static int getMinutes(int totalSeconds){
        return totalSeconds / 60;
    }

    public static int getRemainderSeconds(int totalSeconds){
        return totalSeconds % 60;
    }
}
