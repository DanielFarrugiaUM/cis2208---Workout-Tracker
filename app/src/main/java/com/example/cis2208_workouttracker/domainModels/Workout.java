package com.example.cis2208_workouttracker.domainModels;

import java.io.Serializable;
import java.util.List;

public class Workout implements Serializable {
    public int id;
    public String name;
    public List<Exercise> exercises;

    public Workout() {
    }

    public Workout(String name) {
        this.name = name;
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
