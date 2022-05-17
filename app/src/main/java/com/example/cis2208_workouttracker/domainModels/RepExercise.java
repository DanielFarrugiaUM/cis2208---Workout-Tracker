package com.example.cis2208_workouttracker.domainModels;

public class RepExercise extends Exercise{
    public int noOfReps;

    public RepExercise(long id, String name, int noOfSets, double weight, int noOfReps, long workoutId) {
        super(id, name, noOfSets, weight, workoutId);
        this.noOfReps = noOfReps;
    }

    public RepExercise(String name, int noOfSets, double weight, int noOfReps, long workoutId) {
        super(name, noOfSets, weight, workoutId);
        this.noOfReps = noOfReps;
    }

    public int getNoOfReps() {
        return noOfReps;
    }

    public void setNoOfReps(int noOfReps) {
        this.noOfReps = noOfReps;
    }
}
