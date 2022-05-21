package com.example.cis2208_workouttracker.domainModels;

import android.os.Build;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;

public class HistoryItem {
    //Cannot use the new java.time.LocalDate due to SDK21
    //it needs to be 26 or higher
    long id;
    public Date date;
    public Exercise exercise;

    public HistoryItem(long id, long date, Exercise exercise){
        this.id = id;
        this.date = new Date(date);
        this.exercise = exercise;
    }

    public HistoryItem(Exercise exercise){
        this.date = new Date(System.currentTimeMillis());
        this.exercise = exercise;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.exercise.getName();
    }

    public void setName(String name) {
        this.exercise.setName(name);
    }

    public int getNoOfSets() {
        return this.exercise.getNoOfSets();
    }

    public void setNoOfSets(int noOfSets) {
        this.exercise.setNoOfSets(noOfSets);
    }

    public double getWeight() {
        return this.exercise.getWeight();
    }

    public void setWeight(double weight) {
        this.exercise.setWeight(weight);
    }

    public long getDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    public void setDate(long value){
        this.date.setTime(value);
    }

    public Time getTime(){
        return ((TimedExercise)this.exercise).getTime();
    }

    public void setTime(Time time){
        ((TimedExercise)this.exercise).setTime(time);
    }

    public int getReps(){
        return ((RepExercise)this.exercise).getNoOfReps();
    }
    public void setReps(int reps){
        ((RepExercise)this.exercise).setNoOfReps(reps);
    }

    public int getTotalSeconds(){
        return ((TimedExercise)this.exercise).getTotalSeconds();
    }
}
