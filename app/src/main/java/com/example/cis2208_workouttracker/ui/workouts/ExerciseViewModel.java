package com.example.cis2208_workouttracker.ui.workouts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cis2208_workouttracker.domainModels.Exercise;
import com.example.cis2208_workouttracker.domainModels.RepExercise;
import com.example.cis2208_workouttracker.domainModels.TimedExercise;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ExerciseViewModel extends ViewModel {
    private final MutableLiveData<List<Exercise>> exercises;

    public ExerciseViewModel(){
        exercises = new MutableLiveData<>();
    }

    public MutableLiveData<List<Exercise>> getExercises(){
        ArrayList<Exercise> newExercises = new ArrayList<>();
        //Hard coded data, needs to come from DB
        RepExercise ex1 = new RepExercise("Exercise1", 3,15, 12);
        RepExercise ex2 = new RepExercise("Exercise1", 5,12.5, 8);
        TimedExercise ex3 = new TimedExercise("timed", 4, 0, new Time(0,1,30));
        newExercises.add(ex1);
        newExercises.add(ex2);
        newExercises.add(ex3);
        exercises.setValue(newExercises);
        return exercises;
    }
}
