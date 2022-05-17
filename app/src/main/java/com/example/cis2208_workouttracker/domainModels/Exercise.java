package com.example.cis2208_workouttracker.domainModels;

public abstract class Exercise{
    public long id;
    public String name;
    public int noOfSets;
    public double weight;
    public long workoutId; //FK to workout in DB

    public Exercise(long id, String name, int noOfSets, double weight, long workoutFK) {
        this.id = id;
        this.name = name;
        this.noOfSets = noOfSets;
        this.weight = weight;
        this.workoutId = workoutFK;
    }

    public Exercise(String name, int noOfSets, double weight, long workoutFK) {
        this.name = name;
        this.noOfSets = noOfSets;
        this.weight = weight;
        this.workoutId = workoutFK;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfSets() {
        return noOfSets;
    }

    public void setNoOfSets(int noOfSets) {
        this.noOfSets = noOfSets;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getWorkoutId() {
        return workoutId;
    }
}
