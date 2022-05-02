package com.example.cis2208_workouttracker.domainModels;

public class RepExercise extends Exercise{
    public int noOfReps;

    public RepExercise(String name, int noOfSets, double weight, int noOfReps) {
        super(name, noOfSets, weight);
        this.noOfReps = noOfReps;
    }

    public int getNoOfReps() {
        return noOfReps;
    }

    public void setNoOfReps(int noOfReps) {
        this.noOfReps = noOfReps;
    }
}
