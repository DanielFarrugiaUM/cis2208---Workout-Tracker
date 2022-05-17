package com.example.cis2208_workouttracker.ui.workouts;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
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

    public MutableLiveData<List<Exercise>> getExercises(Context context, long fk){

        DbHelper dbHelper = new DbHelper(context);
        ExerciseUtility exerciseUtility = new ExerciseUtility(dbHelper);
        ArrayList<Exercise> newExercises = exerciseUtility.getAllExercisesByFK(fk);

        //Hard coded data, needs to come from DB
/*        RepExercise ex1 = new RepExercise("Exercise1", 3,15, 12, 1);
        RepExercise ex2 = new RepExercise("Exercise1", 5,12.5, 8, 1);
        TimedExercise ex3 = new TimedExercise("timed", 4, 0, new Time(0,1,30), 1);
        newExercises.add(ex1);
        newExercises.add(ex2);
        newExercises.add(ex3);*/
        exercises.setValue(newExercises);
        return exercises;
    }
}
