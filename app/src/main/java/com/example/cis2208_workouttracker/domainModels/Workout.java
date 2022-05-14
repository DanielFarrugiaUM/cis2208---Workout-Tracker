package com.example.cis2208_workouttracker.domainModels;

import java.io.Serializable;
import java.util.List;

public class Workout implements Serializable {
    public long id;
    public String name;
    public List<Exercise> exercises;

    public Workout(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Workout(String name) {
        this.name = name;
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
