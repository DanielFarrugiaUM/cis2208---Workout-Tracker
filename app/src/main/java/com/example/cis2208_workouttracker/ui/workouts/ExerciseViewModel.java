package com.example.cis2208_workouttracker.ui.workouts;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cis2208_workouttracker.backend.DbHelper;
import com.example.cis2208_workouttracker.backend.ExerciseUtility;
import com.example.cis2208_workouttracker.domainModels.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseViewModel extends ViewModel {
    private final MutableLiveData<List<Exercise>> exercises;

    public ExerciseViewModel(){
        exercises = new MutableLiveData<>();
    }

    //We get exercises from db to display in the edit workout activity
    public MutableLiveData<List<Exercise>> getExercises(Context context, long fk){

        DbHelper dbHelper = new DbHelper(context);
        ExerciseUtility exerciseUtility = new ExerciseUtility(dbHelper);
        ArrayList<Exercise> newExercises = exerciseUtility.getAllExercisesByFK(fk);

        exercises.setValue(newExercises);
        return exercises;
    }
}
