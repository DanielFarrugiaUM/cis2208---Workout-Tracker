package com.example.cis2208_workouttracker.domainModels;

public abstract class Exercise{
    public int id;
    public String name;
    public int noOfSets;
    public double weight;
    public int workoutId; //FK to workout in DB


    public Exercise(String name, int noOfSets, double weight) {
        this.name = name;
        this.noOfSets = noOfSets;
        this.weight = weight;
    }

    public int getId() {
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
}
